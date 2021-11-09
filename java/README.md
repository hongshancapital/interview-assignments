# 短链接服务

## 架构思路

1. 用snowflask算法生成分布式id,然后转换62进制，生成短链接。
2. 存入缓存，缓存按LRU淘汰不常用的链接，防止内存溢出。
3. 同时更新持久层。
4. spingboot实现，restful api风格规范实现服务接口。

#### 架构图

![avatar](https://raw.githubusercontent.com/jackieralf3/interview-assignments/master/java/short-url/src/main/resources/structure.png)

#### swagger

地址：http://localhost:8081/swagger-ui.html

#### 单元测试jacoco

报告在 /target/site/jacoco/index.html，覆盖率（97，90）

![avatar](https://raw.githubusercontent.com/jackieralf3/interview-assignments/master/java/short-url/src/main/resources/jacoco.png)