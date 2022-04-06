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



**设计思路参考：**

https://juejin.cn/post/6844904090602848270

https://www.zhihu.com/question/29270034/answer/46446911 

https://tech.meituan.com/2017/04/21/mt-leaf.html



# 2、系统架构
![架构设计图](E:\Repo\short-domain\docs\架构设计图.png)

- 短域名服务采取分布式架构方式，提高服务可用性
- 通过发号器的生成区间段id，程序优先从内存取数，内存ID耗尽则从DB获取。 发号器的底层实现可采用美团的left-segment，或采用阿里TDDL的sequence id生成。其核心思想是通过分段(步长)的方式生成区间id，减少对DB的频繁访问从而提高可用性。
- ID生成后采用转换成62进制生成短码，从而实现压缩效果
- 将短码与源域名保存映射关系，底层存储可采用DB或HBASE等. 本案例使用guava cache作为存储层易于实现
- 可增加一道缓存，减少热门短域名查询对DB的压力
- 重定向问题。 301是永久重定向，302是临时重定向. 若有埋点需求可采用302，若无则使用301减缓服务器压力



# 3、核心流程

**本地实现:**
![发号器流程图(本地实现)](E:\Repo\short-domain\docs\发号器流程图(本地实现).png)

**分布式实现:**
![发号器流程图](E:\Repo\short-domain\docs\发号器流程图.png)

1.使用发号器生成id，本文采取内存ID自增。线上则可采取阿里TDDL sequence的方式生成区间段id，优先内存取数，减少访问DB频。 本文采取的方式为本地实现，通过多线程异步生成id，id生成采用LongAdder。然后将生成的id提前缓存至队列，消费者随机其中一个队列消费id。

2.将id从10进制转换到62进制得到短码,可实现压缩效果。 (6 位 62 进制数可表示 568 亿的数)

3.将短码与源码保存至DB。当短码转源码时，则可查询DB获取

4.为避免热数据频繁访问db，可增加一道缓存，并设置缓存失效时间。



# 4、测试

## 4.1 环境&参数

**application.properties**

```java
# 短码最大长度
length.limit=8
# 生成器选择
generator.selector=local
# id缓存队列数
queue.list.size=8
# id缓存队列长度
queue.size=1000
```

**JVM运行参数配置**:

```
-Xms4096M
-Xmx4096M  
-Xmn3072M 
 -Xss1M
-XX:SurvivorRatio=8 
-XX:MaxTenuringThreshold=5 
-XX:PretenureSizeThreshold=1M 
-XX:+UseParNewGC 
-XX:+UseConcMarkSweepGC
```


## 4.2 性能测试配置

![jmeter参数配置-1](E:\Repo\short-domain\docs\jmeter参数配置-1.png)

![jmeter参数配置-2](E:\Repo\short-domain\docs\jmeter参数配置-2.png)

利用jmeter内置函数 ${__UUID}请求`generateShortDomian`接口模拟生成短码，Jmeter配置如下:

Number of Threads(users):300
Ramp-up period(seconds):15

## 4.3 性能测试报告

![性能测试](E:\Repo\short-domain\docs\性能测试.png)

![性能测试-2](E:\Repo\short-domain\docs\性能测试-2.png)

压测时间持续至2分钟时，开始出现错误率。QPS约为1.54w/s ,错误率约为0.04% ，平均RT约18ms

压测时间持续至4分钟时，开始出现错误率。QPS约为1.27w/s ,错误率约为0.09% ，平均RT约22ms

压测时开了8个缓存队列，可适当调整队列数以提高吞吐量。



## 4.4单元测试

![jacoco测试报告](E:\Repo\short-domain\docs\jacoco测试报告.png)

## 4.5功能测试

![swagger2](E:\Repo\short-domain\docs\swagger2.png)

长转短:
![长转短](E:\Repo\short-domain\docs\长转短.png)
短转长:
![短转长](E:\Repo\short-domain\docs\短转长.png)

# 5、展望

1.ID发号器可采取分布式方式实现，实现方式可基于阿里TDDL Sequence生成区间id的方式，或者基于美团left-segment方式。2个方式均基于数据库生成区间段id的思想，同时利用分库可提高id发号器的可用性。

2.缺乏重复性校验功能，有可能造成一个长链重复发起请求申请了多个短链。可采取缓存的方式将长链与短链映射，若长链已存在对应的短链则直接返回，若不存在或过期则生成新的短链。缓存淘汰机制可采用LRU的方式

3.缺乏监控高级功能，无法感知ID发号器达到的水位，若无限增长或被刷可能导致生成的短链变的很长。



# 6、联系信息
**基本信息** 

电话：15607119112    Email：709233178@qq.com

**工作经历**

☆阿⾥巴巴(lazada)    ⾼级开发⼯程师    2019/3~至今

> 荣誉&成就: 
> 1.在稳定性建设⽅⾯表现突出，被leader定位成招商平台项⽬组稳定性负责⼈；
> 2.有过大型项目PM经验，从⽅案设计、资源协调、人力安排, 到推动项⽬落地有成熟的经验;
> 3.获得公司层⾯【2020⼤促明星PM】奖项 ,并拿到所在事业部LAZADA期权

☆中农⽹    java研发⼯程师    2017/7 ~ 2018/11

> 荣誉&成就:
> 1.担任丝路通项⽬组开发经理,负责项目需求拆解、资源协调、项目进度跟踪等工作，以及订单业务模块开发
> 2.担任基础服务平台组核⼼发开之⼀，完成公司基础服务能力沉淀，对业务开发团队提供技术支撑

☆中国平安财产保险股份有限公司    java研发⼯程师    2016/11 ~ 2017/6

> 《平安好车主》理赔模块后端开发

☆嘉丰⾦融保险股份有限公司    java⼯程师    2015/7 ~ 2016/10