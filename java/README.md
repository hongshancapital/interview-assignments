# 设计文档
## 需求分析
- 短域名需求主要是需要能通过原始较长域名生成一个唯一的短域名
- 不需要保证相同原始域名生成相同短域名
- 可以使用雪花算法生成趋势递增的long id然后转换成短字符串来实现短域名

## 假设
- 相同url生成的短url不需要相同
- 生成的短url未被访问后会失效
- 考虑到实际可能并发，把Snowflake算法中的sequence bit调整为1位，worker bit调整
为3位。既单worker每毫秒可以生成2个短域名，最多可以支持8个worker

## 系统架构
- 整个架构设计的比较简单, 主要设计思想是接口驱动开发
- api层定义对外的接口形式，以及swagger UI，做到实现和文档分离
- controller层以rest http api的形式实现了api层
- service层实现核心业务逻辑
  - ShortUrlService做主题业务调度
  - UrlGenerator完成通过原始url生成短url
- repository层完成映射的存储


## 测试报告
![Jacoco Report](./test_report.png)

## 性能测试
性能测试使用的工具位[hey](https://github.com/rakyll/hey)
性能测试结果符合预期, 约2000每秒
~~~shell
at 18:47:38 ❯ hey -z 20s -m POST http://localhost:8080/\?originUrl\=https://www.baidu.com

Summary:
  Total:	20.0256 secs
  Slowest:	0.1345 secs
  Fastest:	0.0004 secs
  Average:	0.0252 secs
  Requests/sec:	1985.3630


Response time histogram:
  0.000 [1]	|
  0.014 [10062]	|■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
  0.027 [11475]	|■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
  0.041 [11401]	|■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■
  0.054 [6707]	|■■■■■■■■■■■■■■■■■■■■■■■
  0.067 [36]	|
  0.081 [16]	|
  0.094 [10]	|
  0.108 [0]	|
  0.121 [0]	|
  0.135 [50]	|


Latency distribution:
  10% in 0.0060 secs
  25% in 0.0122 secs
  50% in 0.0250 secs
  75% in 0.0379 secs
  90% in 0.0440 secs
  95% in 0.0461 secs
  99% in 0.0481 secs

Details (average, fastest, slowest):
  DNS+dialup:	0.0000 secs, 0.0004 secs, 0.1345 secs
  DNS-lookup:	0.0000 secs, 0.0000 secs, 0.0032 secs
  req write:	0.0000 secs, 0.0000 secs, 0.0016 secs
  resp wait:	0.0251 secs, 0.0002 secs, 0.1296 secs
  resp read:	0.0000 secs, 0.0000 secs, 0.0116 secs

Status code distribution:
  [201]	39758 responses
~~~

## 后续优化
- 在分布式环境通过环境变量传入雪花算法的worker id
- 在分布式环境使用类似redis的缓存代替jvm存储
- 短时间的热点url生成可以通过一定策略做到生成相同的短url