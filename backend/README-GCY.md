# 短链接生成方案
## 一、需求描述
撰写两个 API 接口

- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制

- 短域名长度最大为 8 个字符（不含域名）

递交作业内容

1. 源代码
2. 单元测试代码以及单元测试覆盖率
3. API 集成测试案例以及测试结果
4. 简单的框架设计图，以及所有做的假设
5. 涉及的 SQL 或者 NoSQL 的 Schema，注意标注出 Primary key 和 Index 如果有。

其他

- 我们期望不要过度设计，每一个依赖以及每一行代码都有足够充分的理由。
## 二、需求分析

1. 短域名限制为最大 8 个字符，一般的随机生成算法 UUID、雪花算法，无法直接使用。
2. 短域名系统是一个多读少写的系统，易出现热点key，短时间流量大，需要做好多级缓存与分布式服务。
## 三、整体方案

1. 短域名生成器，采用令牌桶方案，提前生成令牌
   1. 短链令牌 [8位] = 节点id [1 位] + 62进制数 [最大 7 位]
   2. 7 位62进制数可以表示范围为 0-ZZZZZZZ，10 进制范围为 0-3521614606207，足够正常使用。如果令牌数不够，将来扩展位数即可，此处需要考虑数据存储空间。
   3. 节点id作为分片id，实现分布式服务
2. 采用 LRU + Redis 的多级缓存机制，在应对流量瀑布时，减少 Redis、DB 的压力
3. 对于恶意缓存穿透访问，采用空值存储方案，防止请求全打在DB上
4. 对于缓存击穿，通过LRU缓存，保证缓存不会在热点过期
## 四、系统架构
![image.png](https://cdn.nlark.com/yuque/0/2023/png/158703/1675608520429-18e3d0d5-b2cf-460b-90dc-9ed508d57a59.png#averageHue=%23e9c6a3&clientId=u983338ad-a5c2-4&from=paste&height=574&id=u3ed5e8a3&name=image.png&originHeight=1148&originWidth=1356&originalType=binary&ratio=1&rotation=0&showTitle=false&size=77886&status=done&style=none&taskId=u809435a0-d0ee-42cb-bcfc-2eadc90b40c&title=&width=678)
### 创建短链流程图
![image.png](https://cdn.nlark.com/yuque/0/2023/png/158703/1675685229244-91ba0a2f-b8b6-43c8-8dec-3fd476cfa1e2.png#averageHue=%23f7f7f7&clientId=u983338ad-a5c2-4&from=paste&height=682&id=ub9725ffc&name=image.png&originHeight=1364&originWidth=852&originalType=binary&ratio=1&rotation=0&showTitle=false&size=75289&status=done&style=none&taskId=ubc3b827d-8257-4f05-a82b-7a85ce24bc4&title=&width=426)
### 查询短链流程图
![image.png](https://cdn.nlark.com/yuque/0/2023/png/158703/1675685757942-d7d1e5a9-7025-4772-9257-da7399aa7b88.png#averageHue=%23f6f6f6&clientId=u983338ad-a5c2-4&from=paste&height=776&id=ue429b97a&name=image.png&originHeight=1552&originWidth=954&originalType=binary&ratio=1&rotation=0&showTitle=false&size=107398&status=done&style=none&taskId=u174b3894-90dc-4251-b01a-d0aa32a360a&title=&width=477)
## 五、单元测试
### 测试方案
首先对外部依赖进行分析，当前项目引入了 Redis、DB，在编写单元测试代码时，需要将其mock处理，保证单元测试对外无依赖。
在编写业务代码时，需要考虑到代码的可测试性，也就是外部依赖的引入需要是单一途径，保证可以方便的进行mock替换。
Api 测试中，需要考虑到数据的边界情况，如 url 的有效性、长度、空值等。

测试框架：mocha
断言：assert
mock/打桩：sinon
redis mock：ioredis-mock
db mock：使用内存数据库 sqlite
api 测试：supertest
代码覆盖率：nyc
### 单元测试 + Api 集成测试 结果
![image.png](https://cdn.nlark.com/yuque/0/2023/png/158703/1675690932889-93a2fe10-cef0-460a-9277-1b311afd1621.png#averageHue=%23373737&clientId=u983338ad-a5c2-4&from=paste&height=498&id=u9d3b2cff&name=image.png&originHeight=996&originWidth=1138&originalType=binary&ratio=1&rotation=0&showTitle=false&size=173716&status=done&style=none&taskId=udc24baf1-9544-427f-9c83-563b05296a5&title=&width=569)
### 测试覆盖率 - nyc
```bash
-------------------------|---------|----------|---------|---------|-------------------
File                     | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s 
-------------------------|---------|----------|---------|---------|-------------------
All files                |   92.78 |    78.43 |    92.3 |   93.13 |                   
 src                     |      75 |      100 |       0 |      75 |                   
  app.ts                 |     100 |      100 |     100 |     100 |                   
  server.ts              |       0 |      100 |       0 |       0 | 3-5               
 src/cache               |   66.66 |        0 |       0 |   66.66 |                   
  RedisFactory.ts        |      50 |        0 |       0 |      50 | 10-14             
  lruCache.ts            |     100 |      100 |     100 |     100 |                   
 src/configs             |   84.61 |       40 |     100 |   84.61 |                   
  default.config.ts      |     100 |      100 |     100 |     100 |                   
  dev.config.ts          |     100 |      100 |     100 |     100 |                   
  index.ts               |   73.33 |       40 |     100 |   73.33 | 13-14,19-20       
  production.config.ts   |     100 |      100 |     100 |     100 |                   
  resCode.config.ts      |     100 |      100 |     100 |     100 |                   
  test.config.ts         |     100 |      100 |     100 |     100 |                   
 src/controllers         |   95.12 |      100 |     100 |   95.12 |                   
  ShortLinkController.ts |   95.12 |      100 |     100 |   95.12 | 45-46             
 src/daos                |     100 |      100 |     100 |     100 |                   
  ShortLinkDao.ts        |     100 |      100 |     100 |     100 |                   
 src/domains             |     100 |      100 |     100 |     100 |                   
  ResData.ts             |     100 |      100 |     100 |     100 |                   
 src/models              |     100 |      100 |     100 |     100 |                   
  ShortLink.ts           |     100 |      100 |     100 |     100 |                   
  config.ts              |     100 |      100 |     100 |     100 |                   
 src/routers             |     100 |      100 |     100 |     100 |                   
  index.ts               |     100 |      100 |     100 |     100 |                   
 src/services            |   91.17 |    68.75 |     100 |   93.75 |                   
  ShortLinkService.ts    |   91.17 |    68.75 |     100 |   93.75 | 42,53             
 src/tokenGenerators     |     100 |      100 |     100 |     100 |                   
  SeqTokenGenerator.ts   |     100 |      100 |     100 |     100 |                   
  TokenGenerator.ts      |       0 |        0 |       0 |       0 |                   
 src/utils               |     100 |       75 |     100 |     100 |                   
  ResUtil.ts             |     100 |      100 |     100 |     100 |                   
  digitalUtil.ts         |     100 |       50 |     100 |     100 | 24                
-------------------------|---------|----------|---------|---------|-------------------
```
## 六、压力测试
#### 机器信息
处理器  2.9 GHz 四核Intel Core i7
内存     16 GB 2133 MHz LPDDR3
#### 测试方式

1. 单机单节点启动 server
2. 单机启动 JMeter 进行压力测试
#### 创建短链接口
测试方式：在 JMeter 中随机生成 OriginalUrl，保证每次请求都是不同的 URL，不会命中缓存。
测试参数：50 线程，50s达到满线程
![image.png](https://cdn.nlark.com/yuque/0/2023/png/158703/1675692778743-a3886d1c-95d1-4844-bb7d-ebb8725f9443.png#averageHue=%23424648&clientId=u855df983-e20e-4&from=paste&height=68&id=u1e065dce&name=image.png&originHeight=136&originWidth=1980&originalType=binary&ratio=1&rotation=0&showTitle=false&size=47055&status=done&style=none&taskId=u328be31c-30bd-43d9-8ff0-3068cf0295c&title=&width=990)
![image.png](https://cdn.nlark.com/yuque/0/2023/png/158703/1675692840772-9725cbc1-538a-46ee-829b-74ab393ec321.png#averageHue=%23edf2f7&clientId=u855df983-e20e-4&from=paste&height=552&id=u17332ba7&name=image.png&originHeight=1104&originWidth=1954&originalType=binary&ratio=1&rotation=0&showTitle=false&size=82229&status=done&style=none&taskId=u3e66250a-d5f0-4147-a19c-43c315e6df2&title=&width=977)
![image.png](https://cdn.nlark.com/yuque/0/2023/png/158703/1675692911465-a525ca0c-2457-44e7-a0e3-cc85862f478f.png#averageHue=%23eff3f8&clientId=u855df983-e20e-4&from=paste&height=533&id=u5f4f3549&name=image.png&originHeight=1066&originWidth=1936&originalType=binary&ratio=1&rotation=0&showTitle=false&size=48946&status=done&style=none&taskId=u06dac138-8f2a-44eb-aae3-dd79ab5aa43&title=&width=968)
![image.png](https://cdn.nlark.com/yuque/0/2023/png/158703/1675692855012-5d3f266f-2234-42bf-a274-40907781fd69.png#averageHue=%23edf1f8&clientId=u855df983-e20e-4&from=paste&height=395&id=u74258e1b&name=image.png&originHeight=790&originWidth=1938&originalType=binary&ratio=1&rotation=0&showTitle=false&size=50895&status=done&style=none&taskId=u24dbdd1f-265a-431d-9bf9-13b50a0fcf5&title=&width=969)
图表信息：

1. 平均请求时长 54ms，最大值 353，吞吐量514 /sec
2. 在请求线程达到8左右时，CPU利用率已达到峰值
3. 在请求线程数达到12左右时，吞吐量达到均值 520 / sec
4. 内存使用率低

结论：

1. 单节点单线程吞吐量 514，可以通过cluster利用机器多核性能
2. 可以增大 LRU 的缓存数量
#### 查询短链接口
测试方式：对单一短链进行密集访问，模拟热点key
测试参数：50 线程，50s达到满线程
![image.png](https://cdn.nlark.com/yuque/0/2023/png/158703/1675693698398-b2ff7120-5930-4316-853e-27b27f21e05d.png#averageHue=%23424648&clientId=u855df983-e20e-4&from=paste&height=67&id=udc21f458&name=image.png&originHeight=134&originWidth=1976&originalType=binary&ratio=1&rotation=0&showTitle=false&size=46522&status=done&style=none&taskId=u0ff9b2d2-1fdd-4fe8-9727-ac8a036b874&title=&width=988)
![image.png](https://cdn.nlark.com/yuque/0/2023/png/158703/1675693711305-f65d697f-66dc-4cd9-890d-507c621d6fba.png#averageHue=%23edf1f7&clientId=u855df983-e20e-4&from=paste&height=556&id=ub83f6581&name=image.png&originHeight=1112&originWidth=1946&originalType=binary&ratio=1&rotation=0&showTitle=false&size=70565&status=done&style=none&taskId=u81f62754-7f53-4573-ad4e-93d7838543a&title=&width=973)
![image.png](https://cdn.nlark.com/yuque/0/2023/png/158703/1675693725473-393db3ed-7285-497a-a7f5-10e2b0a52a7a.png#averageHue=%23eef2f8&clientId=u855df983-e20e-4&from=paste&height=549&id=u64ab6c18&name=image.png&originHeight=1098&originWidth=1938&originalType=binary&ratio=1&rotation=0&showTitle=false&size=53978&status=done&style=none&taskId=ud5cc3eae-3624-4b0a-882b-3c6b8ab324f&title=&width=969)
![image.png](https://cdn.nlark.com/yuque/0/2023/png/158703/1675693738040-314e6d22-7ee5-4cd2-84d2-87f95a612ab4.png#averageHue=%23eaeef5&clientId=u855df983-e20e-4&from=paste&height=406&id=u603d0b7c&name=image.png&originHeight=812&originWidth=1962&originalType=binary&ratio=1&rotation=0&showTitle=false&size=58248&status=done&style=none&taskId=uf51a4c69-020d-430f-9710-67c37098447&title=&width=981)
图表信息：

1. 平均请求时长 5ms，最大值 83ms，吞吐量4850 /sec
2. 在请求线程达到3左右时，CPU利用率已达到峰值
3. 在请求线程数达到9左右时，吞吐量达到均值 4800 / sec
4. 内存使用率低

结论：

1. 单节点单线程吞吐量 4850，可以通过cluster利用机器多核性能
2. 可以增大 LRU 的缓存数量
## 七、后续可优化点

1. 对于恶意访问空链接场景，还可以采用bloom过滤器过滤空链接进行优化
2. 查询db数据时，可以使用覆盖索引的方式去优化查询，避免回表
3. 对于并发写入相同OriginalUrl 场景，有概率会生成多条 shortUrl 指向相同的 originalUrl。从业务上，多条数据并不会影响业务使用。从技术上，如果要解决此问题，需要引入分布式锁、消息队列、唯一索引等方案，增加复杂度，降低性能，且没有太大业务效果。
4. 可以有多个层级的号牌生成器，对于VIP用户生成的号牌更短，对于普通用户生成的号牌较长
5. 单节点扩展多节点
6. 针对本服务，生成号牌时，对等待的容忍度较高。从易扩展性的角度，可以将号牌生成器单独剥离成一个服务，统一对外提供获取号牌功能
