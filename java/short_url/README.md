
# Java Assignment

###实现短域名服务

**用例图**

![usecase](https://github.com/hardenCN/interview-assignments/raw/master/java/short_url/doc/usecase.jpg)


**撰写两个 API 接口：**


>  假设1：大量IO非计算密集服务，放弃``一请求一线程同步阻塞`` 改成 ``响应式异步非阻塞``，拥抱 ``eventloop`` ，增加吞吐量？
> 
>  假设2：短域名读多写少，假设要这个服务的时间复杂度为O(1)，用什么数据结构存？（空间换时间？）
> 
>  假设3：相同长网址 ``urlEncode(longUrl)`` 生成相同的短网址，做到幂等性？

![class](https://github.com/hardenCN/interview-assignments/raw/master/java/short_url/doc/class.jpg)

- **短域名存储接口：接受长域名信息，返回短域名信息**
  1. 验证：长度，格式，token
  2. 根据2-8规则，80%的流量将由20%的长域名生成，考虑用``ConcurrentHashMap``实现 ``LRUCache`` 来存储 ，满量的情况用不到的短域名会被剔除，但是``Map.containsValue()``时间复杂度为O(logN),
     <img alt="LRU" height="320" src="https://github.com/hardenCN/interview-assignments/raw/master/java/short_url/doc/LRU.png"/>
  3. 在此基础上应该考虑结合 ConcurrentHashMap 构造参数，减少``rehash``

   | 参数 | initialCapacity | loadFactor | concurrencyLevel |
  | :----:|:----:|:----:|:----:|
  | 值 | 2 * 1024 * 1024 | 1.0f | 8 |
  | 说明 | 1个长域名映射记录(UTF-8)限制1KB，LRUCache 2G内存 | 为了cache size和ConcurrentHashMap threshold保持一致，减少一次rehash | webflux work线程数量为6，这里设置8 |


  * 为了实现【``常数级别时间复杂度``】，两种方案：
  1. 一个容器方案，(不幂等)长域名不判断重复(一次请求一条记录)，允许长域名重复：``Key-Value : 短域名-长域名``
  2. 两个容器方案（用一个容器两条记录代替），(幂等)长域名不允许重复(判断是否存在长域名记录，有则直接返回不新增)，一次请求两条记录: ``Key-Value : 长域名-短域名``，存储后建立反向索引 ``Key-Value : 短域名-长域名`` 空间换时间


- **短域名读取接口：接受短域名信息，返回长域名信息。**
  > 验证：长度，格式；据短域名id，常数级别复杂度直接定位长域名
  > 
  > 对于查询接口，要支持跨域请求，后端 ``CORS`` 或 ``jsonp``跨域。


**限制：**
- **短域名长度最大为 8 个字符**

  > 短域名生成，常规操作：AtmoticLong 生成唯一数字id，转 ``62进制`` 字符串
  > 
  > 我这里参考了 [hashids](https://hashids.org/) ；
  > 
  > **假设，短域名如果出现 ``FuckFuck`` ``ShitShit`` 这种单词怎么办? ``hashid``帮我们解决了这个问题**
  
![jacoco](https://github.com/hardenCN/interview-assignments/raw/master/java/short_url/doc/badhash.jpg)


- **采用SpringBoot，集成Swagger API文档；**
  > ``springboot-webflux``
- **JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图)；**

![jacoco](https://github.com/hardenCN/interview-assignments/raw/master/java/short_url/doc/jacocoReport.jpg)

  > 可能是我用了一些函数式编程，我好像遇到了一个jacoco和lambda 表达式的 [问题](https://github.com/jacoco/jacoco/issues/885) 无法做到**数据上**的行级覆盖 :(

- **映射数据存储在JVM内存即可，防止内存溢出；**
  > 已限制LRU缓存为2G（每条记录最大1KB，每次记录2条(最大2KB)，则最大缓存映射条数1024*1024=1048576条 ）
  > 
  > 给堆空间2.5G，使用G1垃圾回收算法，默认启动 AdaptiveSizePolicy 动态调整即可


**加分项**

- 系统性能测试方案以及测试结果


![jmeter1](https://github.com/hardenCN/interview-assignments/raw/master/java/short_url/doc/jmeter1.jpg)
![jmeter1](https://github.com/hardenCN/interview-assignments/raw/master/java/short_url/doc/jmeter2.jpg)
