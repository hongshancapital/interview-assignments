# 短域名服务设计方案

##需求

撰写两个 API 接口:
- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制：
- 短域名长度最大为 8 个字符
- 采用SpringBoot，集成Swagger API文档；
- JUnit编写单元测试, 使用Jacoco生成测试报告(测试报告提交截图)；
- 映射数据存储在JVM内存即可，防止内存溢出；



##接口设计原则
- 采用restful风格进行接口设计
- 短域名存储接口要求幂等，同一个长域名返回相同短域名
- 同理，同一个短域名只对应一个长域名
- 接口基于token鉴权


##设计方案
### 短域名生成算法

短域名采用随机数生成法，将随机数转化为62进制作为短域名。  
采用java自带的UUID作为随机数(单机测试QPS可达到30W)，然后将随机数和短域名最大整数&，得到的结果保证转化为62进制不超过8位。  
最大整数计算方法:先算出62的8次方的值，然后求出2的对数，得到最大整数为2的47次方。  
代码如下:

```java
 public static final Long MAX_SHORT_CUT_LONG = Double.valueOf(Math.pow(2, 47)).longValue() - 1;
 Long id = UUID.randomUUID().getMostSignificantBits() & MAX_SHORT_CUT_LONG;
 String shortcut = covertLong2Str(id, 62);

```

### 存储选型
由于存储为JVM内存存储，且存储格式为KV型，调研及性能测试后最终选择caffeine作为存储层实现，相对比concurrentHashMap性能更好。
caffeine设置最大容量，防止内存溢出


### 系统架构

![系统架构](https://static01.imgkr.com/temp/daeb8414de414d2aa9d66ad8b9290d4b.jpeg)


### 接口设计及实现

### 写接口
####接口协议
```
curl -X POST \
  http://localhost:8080/api/v1/shortcut \
  -H 'Content-Type: application/json' \
  -H 'Shortcut-Token: abcdefgh' \
  -d '{
	"url" : "http://www.baidu.com",
	"token":"abcdefgh"
}'

{"shortcut":"https://shortcut.cn/9PbqYVw5","code":"000000","msg":"SUCCESS"}

```

####流程图
![读接口](https://static01.imgkr.com/temp/06e1c677a24b444385829e1a77cec0cb.jpeg)


#### 读接口
####接口协议
```
curl -X GET \
  http://localhost:8080/api/v1/shortcut/9PbqYVw5 \
  -H 'Shortcut-Token: abcdefgh'
  
{"url":"http://www.baidu.com","code":"000000","msg":"SUCCESS"}

```

####流程图
![写接口](https://static01.imgkr.com/temp/67d637a607ca447e9fcd703253c367b8.jpeg)


## 性能测试方案
利用JMH对代码进行分层测试，分别测试随机短域名生成qps，repo层qps，api层aps。

### 代码
```java
//测试短域名生成性能
@Benchmark
@BenchmarkMode(Mode.Throughput)
@Threads(100)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 20, time = 2)
@OutputTimeUnit(TimeUnit.SECONDS)
public void measureShortcutThroughput() throws InterruptedException {
 shortcutUtil.generatorRandomStr();
}
 
//测试生成后put到ConcurrentHashMap
@Benchmark
@BenchmarkMode(Mode.Throughput)
@Threads(100)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 20, time = 2)
@OutputTimeUnit(TimeUnit.SECONDS)
public void measureGenerateAndPut() throws InterruptedException {
 String url = UUID.randomUUID().toString();
 String id = shortcutUtil.generatorRandomStr();
 String result = map.putIfAbsent(url, id);
}

//测试生成后put到caffeine
@Benchmark
@BenchmarkMode(Mode.Throughput)
@Threads(100)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 20, time = 2)
@OutputTimeUnit(TimeUnit.SECONDS)
public void measureGenerateAndPutByCache() throws InterruptedException {
 String url = UUID.randomUUID().toString();
 String id = shortcutUtil.generatorRandomStr();
 shortCut2UrlMap.put(url,id);
}

//测试service层性能
@Benchmark
@BenchmarkMode(Mode.Throughput)
@Threads(100)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 20, time = 2)
@OutputTimeUnit(TimeUnit.SECONDS)
public void measureService() throws InterruptedException {
 String url = UUID.randomUUID().toString();
 shortcutService.createShortcut(url);
}


//测试controller层性能
@Benchmark
@BenchmarkMode(Mode.Throughput)
@Threads(100)
@Warmup(iterations = 5, time = 2)
@Measurement(iterations = 20, time = 2)
@OutputTimeUnit(TimeUnit.SECONDS)
public void measureController() throws InterruptedException {
 String url = UUID.randomUUID().toString();
 shortcutController.createShortcut(new ShortcutPostReq(url),"abcdefgh");
}

```
### 结果
```
Benchmark                                   Mode  Cnt       Score       Error  Units
JMHBenchmark.measureController             thrpt   20  121805.231 ± 24298.224  ops/s
JMHBenchmark.measureGenerateAndPut         thrpt   20  112255.971 ± 46475.059  ops/s
JMHBenchmark.measureGenerateAndPutByCache  thrpt   20  184396.638 ±  5515.681  ops/s
JMHBenchmark.measureService                thrpt   20  162075.854 ± 33234.726  ops/s
JMHBenchmark.measureShortcutThroughput     thrpt   20  388914.242 ± 16274.367  ops/s
```
压测可以看出caffeine的性能要优于concurrentHashMap，最终controller层qps达到了10W+。