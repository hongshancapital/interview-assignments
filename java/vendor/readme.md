### 设计思路
    1，短网址服务的核心要义是，把一个长网址和一个短网址一一对应起来，向系统中添加长网址的时候，给用户唯一个短网址，当用户给一个短网址的时候，能够唯一确定的给一个长网址。
    通常情况下，可以设计一个数据库，数据库如下
    create table short_url_info (
	    id bigint(64) auto_increment primary key,
	    long_url varchar(256) not null default "",
	    short_url char(8) not null default "",
	    creat_time int(32) not null default 0,
	    index(long_url(16)),
	    unique(short_url)
    ) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8mb4
    向数据库添加数据时，记录下长网址和短网址，通过唯一索引保证唯一性，但是题目中明确要求数据存储到内存中(jvm)，因此不能使用数据库记录，但是我们可以用map来存储映射关系。

    2，唯一短码生成方案，设计时采用自增id来实现，然后将id转换成62进制数据，通过计算可知8位62进制数据最大是218万亿，足够使用了。

    3，在向系统添加数据的时候，由于map是线程不安全的，因此会存在并发问题，需要锁来保证数据安全，读写锁是一种比较好的选择，提高系统吞吐量。

    4，为了减少锁冲突，可以手动实现分段锁，可以有效的降低锁冲突，提高系统吞吐量。
### 简单验证
    curl -X POST http://127.0.0.1:8082/domain/add_url -d 'url=1234'
    {"code":0,"data":{"url":"00000001"},"msg":"msg"}

    curl -X GET 'http://127.0.0.1:8082/domain/get_url?url=00000001'
    {"code":0,"data":{"url":"1234"},"msg":"msg"}

### 结果验证
    benchmark基准测试结果，10000次写入的平均用时是1.235微秒
