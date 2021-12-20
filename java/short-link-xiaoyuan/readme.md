如有格式问题可访问(https://www.yuque.com/docs/share/17e99d09-c21d-4ad6-a07b-8d2dc7f712dd)
# 1、需求
#### 实现短域名服务（细节可以百度/谷歌）
撰写两个 API 接口:

- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制：

- 短域名长度最大为 8 个字符
- 采用SpringBoot，集成Swagger API文档；
- JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图)；
- 映射数据存储在JVM内存即可，防止内存溢出；

​

**通过互联网了解到短域名的知识：**

[https://leetcode-cn.com/circle/discuss/EkCOT9/](https://leetcode-cn.com/circle/discuss/EkCOT9/)

[https://segmentfault.com/a/1190000012088345](https://segmentfault.com/a/1190000012088345)

[https://juejin.cn/post/6844904152338808845](https://juejin.cn/post/6844904152338808845)
# 2、系统架构
![image.png](https://cdn.nlark.com/yuque/0/2021/png/2437188/1639900397537-d8205269-2d72-4e46-960d-9fd5731a3857.png#clientId=u460061b9-b52c-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=382&id=u145f227a&margin=%5Bobject%20Object%5D&name=image.png&originHeight=519&originWidth=999&originalType=binary&ratio=1&rotation=0&showTitle=false&size=74767&status=done&style=none&taskId=ucfdbd0ff-ebf3-4c72-978a-8bc2a18decd&title=&width=734.5)
解释：

1、为了提高短域名的服务，采用分布式的架构

2、每台服务从ZK获取自己唯一的ID,最前面可用Nginx等负载均衡器进行分流

3、每个服务里面有N个发号器，发号器的实现可以有多种实现。发号器实质是一个码号产生器，可用UUID、HASH、自增等编码方式，发号器的号码生成规则可以使用单一或组合

4、选择器从N个发号器中选择一个作为T，由T获取一个或多个编码返回

5、最后由存储服务将长、短对应关系进行存储（**本文简单使用guava缓存存储**），提供后续的查询

6、**对于8位短链符号，机器编号占N位，发号器编号暂M位，号码占用K位,满足M+N+K=8**

# 3、具体实现&核心算法
### 3.1 自增发号器实现(有缓存)
```java
public CacheQueueNumberGenerator(Long id, Long highMaxConfig, Long stepConfig, Integer queueSizeConfig) {

        this.id = id;
        this.highMax = highMaxConfig;
        this.step = stepConfig;
        //todo 判断是否有持久化的high,如果没有都初始化成0
        Long highConfig = 0L;
        this.high = highConfig;
        this.low = highConfig;

        queue = new ArrayBlockingQueue<>(queueSizeConfig);

        //后台线程
        Thread thread = new Thread(() -> {
            while (true) {
                if (this.low >= this.highMax) {
                    log.error("已用尽号码,停止后台线程,id={},low={},high={},step={},highMax={}", id,low, high, step, highMax);
                    return;
                }
                //如果低水位达到高水位,高水位上移
                if (low >= high) {
                    high += step;
                    //高水位不可越过最高水位
                    if (high >= highMax) {
                        high = highMax;
                    }
                    //todo 持久化记录high
                }
                try {
                    //采用阻塞方式放入元素
                    queue.put(low);
                } catch (InterruptedException e) {
                    log.info("[CacheQueueNumberGenerator] daemon thread interrupted", e);
                }
                //低水位移动
                low++;
                //todo 可以加提前预警，比如low>highMax*0.8
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
```
![image.png](https://cdn.nlark.com/yuque/0/2021/png/2437188/1639903394437-5a0d8056-14c8-47c7-a478-c3c3a706e24c.png#clientId=u7d376f79-0c33-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=557&id=u7f198f71&margin=%5Bobject%20Object%5D&name=image.png&originHeight=847&originWidth=476&originalType=binary&ratio=1&rotation=0&showTitle=false&size=47056&status=done&style=none&taskId=u796e8c09-fd5a-4072-bb04-3b5891e5cc7&title=&width=313)
### 3.2自增发号器实现(无缓存)
```java
public synchronized Long generateCode() {
        if (low >= highMax) {
            log.error("已用尽号码,停止服务,low={},high={},step={},highMax={}", low, high, step, highMax);
            return null;
        }
        //如果低水位达到高水位,高水位上移
        if (low >= high) {
            high += step;
            //高水位不可越过最高水位
            if (high >= highMax) {
                high = highMax;
            }
            //todo 持久化记录high
        }
        Long temp = low;
        //低水位移动
        low++;
        //todo 可以加提前预警，比如low>highMax*0.8
        return temp;
    }
```
![image.png](https://cdn.nlark.com/yuque/0/2021/png/2437188/1639903315412-c905fba3-d04a-42bd-8f74-cf405a88fea6.png#clientId=u7d376f79-0c33-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=749&id=u7f4fc07e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=827&originWidth=426&originalType=binary&ratio=1&rotation=0&showTitle=false&size=40613&status=done&style=none&taskId=u76242839-6282-40a2-9197-2aa5470846b&title=&width=386)
### 3.3 选择器实现(随机)
原理：将所有发号器放入数组array，使用随机数生成器在数组array范围内生成一个随机下标即可
```java
public NumberGenerator selectOneRandom(List<NumberGenerator> numberGeneratorList) {
        //加读锁
        try {
            rwl.readLock().lock();
            if (numberGeneratorList == null || numberGeneratorList.size() <= 0) {
                return null;
            }
            int rnd = new SecureRandom().nextInt(numberGeneratorList.size());
            return numberGeneratorList.get(rnd);
        } finally {
            rwl.readLock().unlock();
        }

    }
```
### 3.4 选择器实现(权重)
原理：将所有发号器放入数组array，权重大的放入多次，选择步骤同3.3同
```java
public NumberGenerator selectOneWeight(List<NumberGenerator> numberGeneratorList) {
        //加读锁
        try {
            rwl.readLock().lock();
            if (numberGeneratorList == null || numberGeneratorList.size() <= 0) {
                return null;
            }
            int[] weight = {0, 0, 0, 0, 1, 2, 3, 4, 5, 5};
            int rnd = new SecureRandom().nextInt(weight.length);
            return numberGeneratorList.get(weight[rnd]);
        } finally {
            rwl.readLock().unlock();
        }

    }
```
​

# 4、单元&功能测试
**配置说明（application.properties）：**

#每位编号的进制
app.config.redix = 62

#短链接总长度
app.config.totalBit = 8

#机器占位
app.config.machineBit = 1

#计数器占位
app.config.counterBit = 1

#缓存过期时长
app.config.expireSec = 3600

#发号器选择（emit、counter）
app.model = emit

**特变注意,还需要新建一个machineId文件,里面是当前微服务的ID。这里可以加拓展，用zooKeeper记录机器ID每台服务启动时去zookeeper获取**
### 4.1 项目目录
![image.png](https://cdn.nlark.com/yuque/0/2021/png/2437188/1639904201496-dd1cec88-fc83-4837-bada-7f6ce73348bb.png#clientId=u7d376f79-0c33-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=350&id=u8ac546c1&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1294&originWidth=1062&originalType=binary&ratio=1&rotation=0&showTitle=false&size=826593&status=done&style=none&taskId=u7da5da99-7908-4a0c-880e-b3580a9f15e&title=&width=287)
### 4.2 单元测试完成度
![image.png](https://cdn.nlark.com/yuque/0/2021/png/2437188/1639904666785-21ed0848-19df-446d-9bfe-79a5fe016eb5.png#clientId=u7d376f79-0c33-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=388&id=ufafba34e&margin=%5Bobject%20Object%5D&name=image.png&originHeight=776&originWidth=2316&originalType=binary&ratio=1&rotation=0&showTitle=false&size=445060&status=done&style=none&taskId=ua9d8a138-664a-4ca9-a5d8-315daa9d103&title=&width=1158)
### 4.3 功能测试
1、获取短链
![image.png](https://cdn.nlark.com/yuque/0/2021/png/2437188/1639905159270-aecf39b3-0bc6-4c8c-9274-51ad9c0494e1.png#clientId=u7d376f79-0c33-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=190&id=u939c37ad&margin=%5Bobject%20Object%5D&name=image.png&originHeight=440&originWidth=1340&originalType=binary&ratio=1&rotation=0&showTitle=false&size=115326&status=done&style=none&taskId=u0e4d5826-b14b-4b29-b219-b47d3736382&title=&width=579)
2、通过短链获取长链
​

![image.png](https://cdn.nlark.com/yuque/0/2021/png/2437188/1639905175993-0d192d02-8b60-4103-b7b3-8dd5b8ace11c.png#clientId=u7d376f79-0c33-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=208&id=u51ea7c4d&margin=%5Bobject%20Object%5D&name=image.png&originHeight=462&originWidth=1272&originalType=binary&ratio=1&rotation=0&showTitle=false&size=127312&status=done&style=none&taskId=u00215ad0-5556-47e5-9ef5-5a882ca46c7&title=&width=573)
### 4.4 swagger API
![image.png](https://cdn.nlark.com/yuque/0/2021/png/2437188/1639905809865-b06474a8-79b7-43f0-bad0-81a65189d669.png#clientId=u7d376f79-0c33-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=334&id=udd9b7305&margin=%5Bobject%20Object%5D&name=image.png&originHeight=1822&originWidth=3298&originalType=binary&ratio=1&rotation=0&showTitle=false&size=742070&status=done&style=none&taskId=ub4d8ba60-13ee-4ea1-9b9f-99ee50f1fce&title=&width=604)
# 5、性能测试
**测试工具**
jmeter
**机器配置**（办公电脑,开了其他软件,如idea）：

处理器：2.6 GHz 六核Intel Core i7
内存：16 GB 2667 MHz DDR4

**JVM配置(保守配置，用2G堆内存，1.6G年轻代)**：

   - 版本：JDK1.8.0_151
   - JVM设置：-Xms2048M -Xmx2048M -Xmn1640M -Xss1M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=5 -XX:PretenureSizeThreshold=1M -XX:+UseParNewGC -XX:+UseConcMarkSweepGC
   - 62个缓存队列

**测试结果：**
![image.png](https://cdn.nlark.com/yuque/0/2021/png/2437188/1639918400744-f4694d81-c571-4407-9196-62c62e241975.png#clientId=u7d376f79-0c33-4&crop=0&crop=0&crop=1&crop=1&from=paste&height=139&id=ua1ad06d7&margin=%5Bobject%20Object%5D&name=image.png&originHeight=480&originWidth=2112&originalType=binary&ratio=1&rotation=0&showTitle=false&size=222476&status=done&style=none&taskId=uab1d644a-fcb1-4c7d-9d0f-fa647f6711f&title=&width=612)
平均响应时间：36ms,最大响应时间360ms
QPS：9195
发现：性能瓶颈在tomcat上，后端开了62个缓存队列，只有0号队列在返回数据，也就是说所有队列都返回的情况下，理论上可达到9k*62 = 54w QPS
# 6、展望

1. 机器ID获取可以改进，使用zookeeper注册并动态获取。(架构图中虚线部分)
1. 由于没有持久化，每次重启服务，编码又从0开始计数。这里可以在生成器中，每次加步长的时候持久化高水位，之后再启动直接读上次的高水位。(架构图中虚线部分)
1. 由于没有持久化，使用缓存记录长、短的对应关系，后续可用分库分表记录这种对应关系，并加上布隆过滤器防止同链接刷单。
1. 单个发号器的码号达到最高水位80%时，可以加上报警功能，提前感知。
1. 此系统可拓展性比较强，可以用来当唯一编号获取系统，比如为订单ID的获取。
1. 性能瓶颈不在算号器上，在tomcat上，可以继续优化tomcat
# 7、作者信息
**基本信息** 

姓名：肖源    地址：杭州市五常街道福鼎家园    电话：13657235345    Email：1214118459@qq.com 

**教育背景**

2014.9--2017.6 武汉理工大学 计算机学院（硕士） 计算机科学与技术 
2010.9--2014.6 武汉理工大学 计算机学院（本科） 物联网工程

**工作经历**

2020.8.28-至今 工作单位: 阿里巴巴-同城-物流 职位：高级JAVA开发工程师 
主要工作：参与近端履约的揽、收、分系统的开发和架构工作，主要使用HSF、分库分表、Tair、Schedulex等技术。

2017.7.3-2020.8.26 工作单位:武汉斗鱼 职位：大数据开发工程师 
主要工作：参与广告RTB实时竞价系统、用户行为收集系统、主播排名系统、任务调度平台的开发，主要使用Hive、Storm、Kafka、Redis、SpringBoot等技术。

2016.6.27-2016.9.2 工作单位：阿里巴巴-阿里健康 职位：java开发实习生 
主要工作：熟悉阿里内部RPC中间件hsf、消息队列notify、分布式数据库中间件tddl等，参与阿里健康APP4.0的后台日志处理、打赏功能开发、天猫项目共建。 
​
