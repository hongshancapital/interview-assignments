#### 实现短域名服务（细节可以百度/谷歌）
## 源地址(https://blog.csdn.net/BCNR2015_qi/article/details/123766215)

撰写两个 API 接口:
- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制：
- 短域名长度最大为 8 个字符
- 采用SpringBoot，集成Swagger API文档；
- JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图)；
- 映射数据存储在JVM内存即可，防止内存溢出；

### 架构图
![image.png](https://img-blog.csdnimg.cn/d80a8b1d611046259bedbb03e0809c35.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQkNOUjIwMTVfcWk=,size_20,color_FFFFFF,t_70,g_se,x_16)
解释：

1、为了提高短域名的服务，采用分布式的架构

2、每台服务从注册中心获取自己唯一的ID,最前面可用Nginx等负载均衡器进行分流，网关层进行认证限流等。

3、短链服务短链生成采用雪花漂移算法，比传统雪花算法更短，虽然是long型id,做62进制转化长度可能为9个字符，但是合理进行参数配置可达到要求：机器码限制3位，也就是最多7个实例；基础时间设置为当前时间；序列设置为4位。此时对生成的id进行62进制转化限制在8个字符，可至少用十年以上，大概13年左右。

4、本服务目前使用guava本地缓存暂存映射。

### 单元测试覆盖率
![image.png](https://img-blog.csdnimg.cn/9b5cdcfaaeb54566b68bef11c2719176.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQkNOUjIwMTVfcWk=,size_20,color_FFFFFF,t_70,g_se,x_16)

### 功能测试
![image.png](https://img-blog.csdnimg.cn/97087c70177e47629032e5a1923b9697.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQkNOUjIwMTVfcWk=,size_20,color_FFFFFF,t_70,g_se,x_16)

### 性能测试
测试工具jmeter
机器配置比较旧（办公电脑,开了其他软件,如idea）：

处理器：2.65GHz 四核Intel Core i5
内存：16 GB 2667 MHz DDR4

JVM配置(保守配置，用2G堆内存，1.6G年轻代)：

   - 版本：JDK1.8.0_151
   - JVM设置：-Xms2048M -Xmx2048M -Xmn1640M -Xss1M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=5 -XX:PretenureSizeThreshold=1M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC

![image.png](https://img-blog.csdnimg.cn/a3c2b2bca5934814b00a80065950fa7f.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQkNOUjIwMTVfcWk=,size_20,color_FFFFFF,t_70,g_se,x_16)

### 展望
1、映射数据没有持久化，未来持久化需进行分库分表来支持高并发。

2、接口可以使用redis对长链对幂等限制；针对高并发利用redis布隆过滤器校验生成短链不存在则将映射插入mysql, 避免多次查库判断等，缓解数据库压力。