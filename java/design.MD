# 设计思路

![Image](https://github.com/oldnoop/interview-assignments/edit/master/java/jacpic.png)

对每一个长链接，

全局自增值，

 每次生成一个自增的10进制的数值，加锁方式递增long型值

 com.oldnoop.shortlink.transfer.SequenceTransfer

将10进制的数值 转换为 62进制，得到短链接编码，

 用 62 个字符 [A-Z, a-z, 0-9][A−Z,a−z,0−9] 来代表一位的话(62进制)。

 com.oldnoop.shortlink.util.Radix62Transfer#to

 设计长度为 n 的短链接，则可以包含会有 62^n 个链接

 将协议 和 域名 和 短链接编码 拼接 得到 短链

 com.oldnoop.shortlink.transfer.SequenceTransfer#transfer

存储

 将生成的短链接 和 原来的长链接 ，分别作为 key和value ，存储到 Map 对象中

 com.oldnoop.shortlink.storage.MemoryLinkStorage

重复长链接处理

 Map使用google guava提供的BiMap，

 可以提供 唯一的 键值对 操作和 反向操作(value-key映射)

 每次先查询是否存在，如果存在，直接返回

并发处理

 采用简单的synchronized加锁方式，每次创建短链接都进行同步

内存溢出

 采用单线程调度任务，定期检查 存储对象的大小，是否超过 配置的阈值，

 超过时拒绝存储抛出异常，待删除短链接 增加空间， 或者 实时调整阈值

短链编码最大长度

 采用可配置的方式，生成短链编码后，检查是否 超过 配置的长度，

 超过，则拒绝抛出异常

统一返回结果

 封装统一的结果对象，做为api调用的返回结果

 com.oldnoop.shortlink.model.ApiResult

统一异常处理

 封装统一的异常处理，作为api调用发生异常时的处理方式，简化编码

 com.oldnoop.shortlink.config.ExceptionAdvice

API文档

 配置类com.oldnoop.shortlink.config.SwaggerConfig

 控制器添加swagger注解

 com.oldnoop.shortlink.controller.ShortLinkController

单元测试

 采用spring-test测试框架，Mockito框架实现Mock, jacoco生成代码覆盖率的测试报告

 ![Image](https://github.com/oldnoop/interview-assignments/edit/master/java/jacoco.png) 

性能测试

 采用JMH框架执行测试，IDE(IDEA，eclipse等) 需安装对应插件

 com.oldnoop.shortlink.JMHLinkCreateTest

 com.oldnoop.shortlink.JMHLinkSearchTest

# 应用场景和假定情况

 1. 暂不考虑分布式场景，分布式情景，需要考虑 下述issue

 全局分布式自增ID

 分布式锁

 共享存储

 2. 暂不考虑存储持久化，该场景下需考虑下述issue

 重复长链接查询性能，引入 布隆过滤器

 3. 暂不考虑短链接删除，该场景下需考虑下述issue

 在分布式情况下，布隆过滤器，删除短链接，还需引入黑名单

 4. 暂不考虑多域名，该场景下需考虑下述issue

 多域名随机分配

 5. 暂不考虑 多个长链接 对应 单个 短链接，

 某个短链接 可 随机转发 到 几个 长链接的其中一个
