# Url-Shortener

## Url-Shortener是什么
Url-Shortener是一个将长域名转短域名的服务，提供两个功能：传入长域名获取短域名；通过短域名查询对应长域名进行重定向。

**1、使用方法**
```
# 打包
mvn package

# jacoco测试报告
mvn test
打开./target/site/jacoco/index.html查看单元测试报告

# 运行
java -jar urlshortener.jar
# 接口
接口:http://localhost:8080/swagger-ui.html
```

## 设计文档
[设计文档](./doc/01-design.md)

## 测试报告
[测试报告](./doc/02-test.md)


## TODO和问题
1. 长URL和短链接一对多实现，一个长链接对应多个短链接
2. 统计，统计每个短链接访问信息
3. 限流实现
