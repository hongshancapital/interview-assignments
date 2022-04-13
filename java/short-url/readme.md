
假设
全部URL总数小于1万亿条。
并发量并不大，并发请求小于50 reqs/秒。
URL长度适中，最长不超过6144个字符。

术语定义
长链接：longURL，长域名，长链。指的是用户输入的长URL，比如https://new.qq.com/omn/20210913/20210913A01JME00.html。
短链接：shortURL，短域名，短链，短码。指的长链接对应的短链接编码，假设域名为a.cn则短链接形如http://a.cn/1234abcd
字符码：64进制字符码。指的是由64个字符[a-zA-Z0-9中划线及下划线]组成的最长不超过8位的字符码，例如上面的1234abcd。
详见rfc4648 Section5 https://datatracker.ietf.org/doc/html/rfc4648

设计思路：

1) 短域名存储接口中，主要涉及2个逻辑，第一个是查询逻辑，第二个逻辑是插入长短域名mapping关系。
关于查询逻辑设计，使用MurmurHash3对长链接进行hash，128 bit重复概率非常小。 （如果对安全性有要求，可以使用MD5，后面逻辑相同）
为了获取较好的查询及插入性能，将hash字段设计成数字类型，从128 bit中截取48 bit，2^48=281,474,976,710,656。同时该数字不超出64的8次方，即满足最大为8个字符的限制条件。

查询时根据长链接hash后的48 bit数字作为key，如果在db中查询不到，则保存该mapping关系。
如果存在，则判断原来保存的长连接是否与本次查询的长链接相同。如果相同，则直接返回原来记录。如果不相同，说明发生了hash碰撞。
解决hash碰撞的办法，在原始长链接后面增加一个特殊字符CTRL-A（\u0001），因为MurmurHash算法的特性，hash后的数字会变化比较大。重复这个步骤，这样直到找到一个之前不存在的hash值。
https://stackoverflow.com/questions/11899616/murmurhash-what-is-it
这样处理后，需要在取出原始长链接时进行一下判断，去掉末尾的所有CTRL-A字符之后再返回。
虽然这样处理可能会有些耗时，但是因为MurmurHash3出现碰撞的概率极低，因此该方案可以接受。

相关数据库表如下所示
CREATE TABLE IF NOT EXISTS `short_url_mapping` (
  `id` int(13) unsigned NOT NULL AUTO_INCREMENT,
  `lurl` VARCHAR(6144) NOT NULL COMMENT '长链接',
  `lurl_hash` BIGINT COMMENT '长链接数字类型hash值，48 bit',
  `create_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX idx_hash (`lurl_hash`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

lurl_hash可以和64进制字符码相互转化，因此db里不保存字符码字段。

2) 短域名读取接口，根据字符码可以得到对应的lurl_hash数字，然后查询short_url_mapping，如果查找到记录，则返回相应的长链接（去掉所有CTRL-A字符）。
如果查询不到，则给出提示信息。

3）本方案设计只是给出一个初步可迭代版本，后续可以根据具体场景需求进行演化，比如增加Query缓存层，数据库表可以分区或者分库分表，使用NOSQL/KV存储方案等。
具体情况可以讨论沟通。