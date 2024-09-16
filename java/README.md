# 1、需求描述

## 实现短域名服务（细节可以百度/谷歌）

**撰写两个 API 接口:**
- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制：
- 短域名长度最大为 8 个字符
- 采用SpringBoot，集成Swagger API文档；
- JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图)；
- 映射数据存储在JVM内存即可，防止内存溢出；

**递交作业内容**
- 源代码(按照生产级的要求编写整洁的代码，使用gitignore过滤掉非必要的提交文件，如class文件)
- Jacoco单元测试覆盖率截图(行覆盖率和分支覆盖率85%+)
- 文档：完整的设计思路、架构设计图以及所做的假设(Markdown格式)

**加分项**
- 系统性能测试方案以及测试结果

# 2、需求拆解&架构设计

从需求来看，核心功能为以下两点（下文中用「短链接」进行描述，与题目中的「短域名」）：
- 生成长链接对应的短链接
- 长链接和短链接的相互映射

结合真实场景和题目给出的限制条件，我们做一些假定，作为方案的设计依据：
- 真实场景中，短链接的地址需要发号器支持。最简单的方案是使用数据库的自增id，但这种方式存在的两个主要问题是：
  - 性能问题：数据库无法承受大量并发。针对这种情况，业界多采用分段申请的方式，比如每次让数据库自增1000，上层的发号器每次自增1，
    等快要用完时去数据库中再次分段申请号段，如此可以很大程度上提升性能，而且保证全局唯一。比如美团的Leaf就是这种思路。
  - 安全问题：自增id可能会被遍历，容易分析出我们的业务情况。业界多采用二次hash的方案进行应对，但不同hash算法可能存在不同程度的碰撞。
    本次题目中，我们使用hashids开源库进行二次编码，注意它有以下几大特性：
    - 不是严格的hash（因为可以反向解析）。我们后续落库的话，可以存储自增id，此时就需要反向解析的功能（根据短链接获取对应的长链接）。
    - 入参只能为0或正整数。由于我们的自增id也是正整数，因此可用。
    - 不会发生冲突。因此我们可以无需考虑冲突碰撞的解决方案。
    - 生成的结果无序（自定义salt）。因此可以保证我们的id无法被遍历，安全性也有保障。
  
  另外，真实场景中都是需要落库的，鉴于题目仅要求「映射数据存储在JVM内存即可」，所以方案会得到一些简化（我们在内存中自增即可）。
  但考虑到开发机的内存容量有限，我们可以使用缓存组件限制内存中最大保存的数据量。这里采用Caffeine，由于它采用了异步操作，并引入了W-TinyLFU的实现，性能和缓存命中率会比Guava Cache高一些。
  
- 题目要求「短域名长度最大为 8 个字符」，所以我们尽可能使用高进制，以此表示更大的数据量，这里我们采用业界通用的62进制编码方案。
- 真实世界中的长连接无穷多，我们不可能穷尽。考虑到实际情况，我们假定该系统服务的领域范围是有限的，也即长链接的数据量是有限的。
- 考虑到系统后期的扩展性，我们将系统设计为分布式架构，主要体现在两种扩展维度：
  - 水平扩展机器。 比如增加机器。所以我们为机器编号。
  - 垂直扩展机器。比如提升单台机器的硬件配置。 
  
  另外，为了减少多线程的资源竞争，我们为每台机器中配置多个发号器。
  最终，我们将8个字符分成多段：其中(0~M)位表示机器编号，(M+1, N)位表示每台机器中的发号器编号，（N+1,8）位用来容纳短链接号码。
  也即协议格式为：|机器编号|发号器编号|短链接号码|
  若剩下的位数不足，则返回错误信息。
  对于本题目来说，我们可以让机器编号和每个机器的发号器的编号都占1位，这样还剩6位可以用来编码，可以容纳的数据量为62^6，约568亿。
  若每台机器部署62个发号器，则为568亿*62，此时可容纳的数据量约3.5万亿。
  我们也可以扩展机器到N台，则为568亿*62*N，N最多可以扩展到62，此时可容纳的数据量约218万亿。
  
  另外，我们这样做有一个好处：无需考虑多台机器、多个发号器之间的同步问题，因为已经在协议层进行隔离了。
  
- 考虑到短链系统可能被恶意利用，比如套娃操作（生成短链接的短链接，并不断递归操作）会消耗大量号码资源。所以我们需要禁止为本域名网址生成短链接。

扩展（暂不实现）：
- 若短链接长度不够用，导致无法发号了，怎么办？
  - 从业务层面来看
    - 长期追踪业务量级。若业务量级增加到一定程度，我们可以考虑放宽短链接长度的限制，比如由8位增加为9位甚至更多。
    - 精细化运营，及时回收过期短链接，后续可以将其释放出来的链接进行复用。比如短链接区分为长期有效和临时有效。进一步地，还可以将临时有效的情况进行细分，比如过期时间自定义。
  - 从技术层面来看  
    - 监控发号器的使用情况。达到一定阈值时发出报警信息，以便提前处理。
- 性能监控
  - 可使用skywalking等apm组件
- 防刷限流
  - 从技术角度考虑，可针对单机或集群进行保护，防止恶意消耗号码资源。
  - 从业务角度考虑，可针接口请求的不同维度进行限流，比如限制某个ip/设备/用户在一定时间内生成短链接的频次。
- 成本核算/商业化
  - 短链接系统一般属于基础服务，势必会涉及到计费，此时可考虑建设多租户架构，分别计费（公司部门成本核算或者商业化均可）。
  - 可按照短链接生成次数、存储时长等不同维度划分不同的计费阶梯。
  
架构图：
- 整体架构
![整体架构图](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/img/imgs/%E7%9F%AD%E9%93%BE%E6%8E%A5-%E6%95%B4%E4%BD%93%E6%9E%B6%E6%9E%84%E5%9B%BE.png)
- 单机架构
![单机架构图](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/img/imgs/%E7%9F%AD%E9%93%BE%E6%8E%A5-%E5%8D%95%E6%9C%BA%E6%9E%B6%E6%9E%84%E5%9B%BE.png)
- Swagger API
![Swagger API](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/Swagger%20API.png)

系统信息：
- 端口：8090
- 服务根路径：/tinyurl
- 接口文档：/tinyurl/swagger-ui.html
- 存储接口：/tinyurl/store
- 读取接口：/tinyurl/fetch

## 参考文档
- [请实现一个短域名的系统设计](https://leetcode-cn.com/circle/discuss/EkCOT9/)
- [短网址系统的原理及其实现](https://hufangyun.com/2017/short-url/)
- [短网址系统设计思路](https://zhuanlan.zhihu.com/p/145047287)
- [短 URL 系统是怎么设计的？](https://www.zhihu.com/question/29270034/answer/46446911)
- [Leaf——美团点评分布式ID生成系统](https://tech.meituan.com/2017/04/21/mt-leaf.html)
- [hashids](https://hashids.org/java/)


# 3、单元测试（功能测试）
使用Junit5框架，结合Spring进行接口层面的测试。覆盖率在80%以上。
- 测试报告截图
![jacoco测试报告](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/jacoco%E6%B5%8B%E8%AF%95%E6%8A%A5%E5%91%8A.png)

# 4、压力测试（性能测试）
配置说明：
- 机器配置：2.2 GHz 4-Core Intel Core i7
- JDK版本：1.8
- JVM参数：-Xms1g -Xmx1g -Xmn500m -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:CMSFullGCsBeforeCompaction=5 -XX:+UseCMSCompactAtFullCollection

## 基准测试
直接在IDE中执行，使用jmh，简单评估发号器函数性能。注意这里只是方法层面，结果会比实际的吞吐量高
  - 测试代码：参见 TinyUrlBenchmarkTest 类。启动2个进程，每个进程150个线程。
  - 测试思路：随机生成长连接，循环调用controller的接口。
  - 测试结果：参见 performancetest/benchmark/benchmarkTest.log 文件，可以看到：只看发号器层的话， Tp99为429毫秒，QPS约1.5万。

## 单机压力测试
直接在本地IDE启动服务，使用jmeter评估发号器性能。
  - 测试思路：开500个线程，循环测试2000次。
  - 测试结果：结果文件参见下图，或访问 performancetest/stand-alone/web_result/index.html查看。
    可以看到Tp99为196毫秒，平均57.16毫秒。QPS为8352.68。
![单机性能测试](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/%E7%9F%AD%E9%93%BE%E6%8E%A5-%E5%8D%95%E6%9C%BA%E6%80%A7%E8%83%BD%E6%B5%8B%E8%AF%95.png)    

相关配置文件可参见 performancetest/stand-alone/tinyurl.jmx，截图如下所示：
![线程组](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/jmeter%E5%8E%8B%E6%B5%8B%E5%8F%82%E6%95%B0%E9%85%8D%E7%BD%AE%EF%BC%88%E7%BA%BF%E7%A8%8B%EF%BC%89.png)
![存储接口](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/jmeter%E5%8E%8B%E6%B5%8B%E5%8F%82%E6%95%B0%E9%85%8D%E7%BD%AE%EF%BC%88%E5%AD%98%E5%82%A8%E6%8E%A5%E5%8F%A3%EF%BC%89.png)

## 扩展:集群搭建
集群部署：准备4个nginx容器，其中一个负责负载均衡，另外三个作为应用服务器。
   - docker相关配置文件目录位于 performancetest/cluster/docker/ 文件夹下。使用命令`docker-compose up --build`部署并启动集群即可。
     ![集群负载均衡](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/img/imgs/%E7%9F%AD%E9%93%BE%E6%8E%A5-%E9%9B%86%E7%BE%A4%E8%B4%9F%E8%BD%BD%E5%9D%87%E8%A1%A1.png)
     ![docker示意图](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/%E7%9F%AD%E9%93%BE%E6%8E%A5-%E9%9B%86%E7%BE%A4%E8%B4%9F%E8%BD%BD%E5%9D%87%E8%A1%A1docker%E7%A4%BA%E6%84%8F%E5%9B%BE.png)
   - 实现负载均衡后，请求就会均匀分散到3台应用服务器上。
    ![负载均衡结果-server1](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/server1.png)
    ![负载均衡结果-server2](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/server2.png)
    ![负载均衡结果-server3](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/server3.png)

注意： 
   - 我们的缓存是单机缓存，针对集群其实需要做分布式缓存。 
   - 容器没有做隔离，所以其实还是共享宿主机的cpu和内存资源。现实中的生产集群需要做资源隔离，才能真正体现出水平扩展的效果。

# 5、项目结构说明
- 服务代码结构
![服务代码结构说明](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/%E6%9C%8D%E5%8A%A1%E4%BB%A3%E7%A0%81%E7%BB%93%E6%9E%84%E8%AF%B4%E6%98%8E.png)
- 项目资源文件
![项目资源文件说明](https://hcxiaoxi-img-submit.oss-cn-beijing.aliyuncs.com/%E9%A1%B9%E7%9B%AE%E8%B5%84%E6%BA%90%E6%96%87%E4%BB%B6%E8%AF%B4%E6%98%8E.png)
- 其他说明
若图片访问失败，可查看imgs/目录下的本地图片文件

# 6、个人信息
- 邮箱: hcxiaoxi@126.com