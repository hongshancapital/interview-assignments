
主要步骤为两个：

A.   核心步骤：

实现唯一发号器.

每个请求获取一个唯一编号，转换成62进制，62进制是由 英文字母(大小写)、阿拉伯数字等格式组合，作为短地址的短码.

B.        发号器规则：


计算的来源思路: Twitter 雪花算法

0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 


1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0.

41位时间截(毫秒级) 这个是毫秒级的时间，一般实现上不会存储当前的时间戳，而是时间戳的差值（当前时间-固定的开始时间），这样可以使产生的ID从更小值开始；41位的时间戳可以使用69年，(1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69年；

5位的数据中心，可以存在在32个数据中心

5位的机器位，每1个数据中心可以部署32个节点

12位序列号，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号

加起来刚好64位，为一个Long型。

C.        进制转换：

通过上述发号器得到的Long类型的数据，转换为62进制，比如:6628238651141500928  转换为: 7TDp0rS917i 下面这个字符串就是需要的短地址,http://127.0.0.1/7TDp0rS917i


下面这个字符串就是需要的短地址。

重定向
通过 curl -i http://127.0.0.1:9527/7TDhjcamrAI 应用会匹配末端的字符串，去redis里面拿到url，然后通过状态码 302 重定向即可。


性能测试
使用 JMH 做性能基准测试，环境为 CPU: 2.2 GHz Intel Core i7; Memory: 16 GB; OS: Mac OSX。

```java
 Options options = new OptionsBuilder().include(BenchmarkTest.class.getName())
.warmupIterations(1) // 预热
.warmupTime(TimeValue.seconds(1))
.measurementIterations(5)// 一共测5轮
.forks(1)// 创建1个进程来测试
.threads(10)// 线程数
.build();
                
```

测试结果如下：

```java
# Fork: 1 of 1
# Warmup Iteration   1: 1310.917 ops/s
Iteration   1: 882.723 ops/s
Iteration   2: 697.985 ops/s
Iteration   3: 543.681 ops/s
Iteration   4: 76.458 ops/s
Iteration   5: 56.504 ops/s


Result "com.java.assignment.AppBenchMark.forIndexIterate":
  451.470 ±(99.9%) 1430.300 ops/s [Average]
  (min, avg, max) = (56.504, 451.470, 882.723), stdev = 371.444
  CI (99.9%): [≈ 0, 1881.770] (assumes normal distribution)

Benchmark                      Mode  Cnt           Score           Error  Units
AppBenchMark.longToShort   thrpt    5        4212.848 ±     6263.858  ops/s
AppBenchMark.shortToLong  thrpt    5         451.470 ±      1430.300  ops/s


```


longToShort 是通过 JMH 直接请求本地方法。dasd100，qps 大概 4000 左右。


shortToLong 是直接调用本地方法服务得到短地址，qps 450 左右，比较理想。


进一步的优化空间可以关注一下进制转换部分，有不必要的基本类型转换。

原理:

Ø  通过发号策略，给每一个请求的长地址分配一个唯一编号，小型系统直接利用数据库的自增主键就可以。

Ø  如果大型应用，可以考虑实现分布式发号器,不断自增就行。第一个使用这个服务的人得到的短地址是http://www.shururl.com/0 第二个是 http:// www.shururl.com /1 第10个是 http://www.shururl.com /a 第依次往后.



增加自定义注解来控制api每秒请求次数，防止攻击

原理:令牌桶


```java
  // 此处设置的是1秒内运行访问10次
    @CurrentLimit(count = 10, timespan = 1)
    @ApiOperation(value = "域名存储", notes = "接受长域名信息，返回短域名信息")
    @GetMapping("/longToShort")
```
    

 


