# 设计报告
    短域名服务，Java 版设计方案报告
    
* 作者：刘华超
* 日期：2021-12-21
* 版本：v1.0
* 邮箱：job@liuhuachao.com

## 设计思路
经过调研，目前网上流行的设计思路如下：
1. 通过某种算法将长地址转成短地址，然后将对应关系存储在映射表
2. 通过 ID 生成器生成唯一的长整型 ID，然后将 ID 转化成 62 进制的字符串（[a - z, A - Z, 0 - 9] ）

第一种存在重复的可能，本例中采用第二种，具体方案如下：
1. ID 生成器使用多个计数器（AtomicLong 原子操作）生成唯一自增 ID
2. 通过进制转换将获取到的 ID 转为 62 进制的唯一字符串，然后加上固定短地址前缀（假设需要），存储时去掉前缀
3. 将短地址和长地址的映射表存储在 ConcurrentLinkedHashMap（使用了 google 团队提供的一个容器，可以用来实现一个基于LRU策略的缓存，防止内存溢出，最大数量设为 100万）
4. 为了过滤重复的长地址，将长地址到短地址的映射关系存储在 LinkedHashMap 中（为防止内存溢出和提高效率只存储最近使用的1万条）
5. 短域名长度最大为 8 个字符，假设需要4位前缀（x.y/），则剩余4位62进制的字符串，总数量为 62^4 >= 1447万，超过100万

## 架构设计
* 本例采用 Spring Boot 框架，DDD 分层架构
![单元测试覆盖率截图](.\src\main\resources\static\img\DDDArchitecture.png )


## Swagger
* 访问地址：http://localhost:8080/swagger-ui.html

## 测试方案
* 单元测试采用 JUnit4
* 使用了 Jacoco Maven 插件，运行 mvn test 后在目录 \target\site\jacoco\ 下生成对应文件
 ![单元测试覆盖率截图](.\src\main\resources\static\img\UnitTestCoverage.png )
* Controller 包的单元测试使用了 MockMvc