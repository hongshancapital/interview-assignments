# 实现短域名服务
### 撰写两个 API 接口:
  
 * 短域名存储接口：接受长域名信息，返回短域名信息
 * 短域名读取接口：接受短域名信息，返回长域名信息。
### 限制：
 ```
  短域名长度最大为 8 个字符
  采用SpringBoot，集成Swagger API文档；
  JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图)；
  映射数据存储在JVM内存即可，防止内存溢出；
```
### 设计思路

* 使用【0-9，a-z，A-Z】62个字符生成8位长度短链，一共有62^8次方=218万亿种不同的短链，足够满足需求。可以将短链视为62进制，那么使用自增或随机数方式生成一个long类型数字，然后转化为62进制表示的字符串即可。
* 为提升系统高可用和吞吐率，使用分布式架构。将218万亿按机器数分为不同的连续区间，每台机器分配一个区间[min, max]，区间两两无交集。
* 因自增和随机生成各有优缺点，使用策略+工厂方式实现不同的生成器，客户端可以自己选择适合自己业务场景的生成器
* 生成策略使用预生成+多队列实现，预生成的号码放在有界队列里，取号时转成短链code，拼接上host即可
* 为了提升效率，使用线程池实现队列的并行存取
* 依托本地Cache缓存短链，查询时可根据短链code计算出long值，再根据每台机器的区间[min, max]，分发到对应的机器上

##### 设计图：
###### 部署架构图：
![image](https://github.com/yangyp8110/interview-assignments/blob/yyp_short_url/java/src/doc/318541572.jpg)

###### 项目结构图：
![image](https://github.com/yangyp8110/interview-assignments/blob/yyp_short_url/java/src/doc/project.jpg)

![image](https://github.com/yangyp8110/interview-assignments/blob/yyp_short_url/java/src/doc/103071676.jpg)

###### 测试结果：
![image](https://github.com/yangyp8110/interview-assignments/blob/yyp_short_url/java/src/doc/test.jpg)
######## 获取短链接
![image](https://github.com/yangyp8110/interview-assignments/blob/yyp_short_url/java/src/doc/test-get-long-url.jpg)
######## 获取长链接
![image](https://github.com/yangyp8110/interview-assignments/blob/yyp_short_url/java/src/doc/test-get-short-result.jpg)
