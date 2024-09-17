## 设计思路

短域名生成接口

* 采用62位随机数生成8位长度的短域名, 可供生成的短域名的数量为1.3E+14个, 预期可以满足未来3年之内的需求。
* 基于ConcurrentSkitListMap在大数据并发读写的优势, 生成短域名方法中使用ConcurrentSkitListMap保存长域名和域名的K-V pair。
* 采用Spring Boot、集成Swagger, 测试采用了Junit单元测试、Jacoco生成测试报告、Jemeter压力测试。

## 架构图
<img src="./src/main/resources/static/短域名应用架构图.png"/>

## 所做的假设
1.  域名总量在1.3E之内。
2.  QPS在500之内。 