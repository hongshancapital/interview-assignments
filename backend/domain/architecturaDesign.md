# 短地址服务设计

## 一、需求

### 1.功能需求

1. 用户可以通过接口将长的URL转换为短的URL。
2. 用户可以通过短的URL访问原始的URL。
3. 需要提供统计数据，包括访问量、转换量等。

### 2.非功能性需求

1. 高可用性：系统应该能够在故障或异常情况下保持可用。
2. 高性能：系统应该能够快速响应请求，处理大量并发访问。
3. 可扩展性：系统应该能够轻松地进行水平扩展以应对用户量的增长。
4. 安全性：系统应该能够轻松地过滤黑名单用户和ip，防止各种流量和恶意攻击。

### 3.扩展要求：（非必需）

1. 自定义短码长度：用户可以指定生成的短码长度。
2. 失效时间：短码可以设置过期时间，过期后无法访问。
3. 是公链还是私密连接。

## 代码地址：https://github.com/floralatin/domain

## 二、架构设计

### 1.架构图

![domain](https://floralatin-blog.oss-cn-beijing.aliyuncs.com/images/projects/domain/architecture_design.png)

### 2.功能模块

1. 分布式ID生成器：
用来生成唯一Id：使用算法41位时间+6位业务ID+6位数据中心ID+10位序列号
然后转换成62进制并取后8位作为短地址code码
2. 用户模块：用户创建和鉴权
3. 统计模块：用来统计短地址被访问信息，以便进行数据分析
4. 短地址模块：用来生成短地址和长地址。在存储到 数据库的时候进行encodeURIComponent 编码 ASCII 码的形式，以确保数据在存储和传输过程中不会出现问题。

### 3. 技术栈

node + express + typescript + redis + mongodb k8s+ docker 部署

### 4. 数据库

最新的全世界网页被索引的49.5亿
每条200字节*49.5亿约等于9.01 TB

1. NoSql文档存储可以快速添加扩展字段。
2. mongodb的分片技术很好的支持海量数据
3. 数据备份： 数据备份不要采用 mongodump/mongorestore 方式，而是采用热备或者文件拷贝方式备份。
4. 很久之前的的数据用户基本上不会访问：冷数据归档到低成本 SATA 盘。

### 5. API设计

- create:

![create](https://floralatin-blog.oss-cn-beijing.aliyuncs.com/images/projects/domain/api_create.png)

- redirect:

![redirect](https://floralatin-blog.oss-cn-beijing.aliyuncs.com/images/projects/domain/api_redirect.png)

### 6. 部署方案

用k8s和 docker 部署

1. 可移植性：容器化隔离了硬件设施环境
2. K8s可以很好的对进行管理和快速的动态水平扩展
3. 管理和监控

### 7. 安全设计

1. API网关
2. 用户鉴权
3. IP黑名单\用户黑名单 防止恶意用户
4. 令牌桶限速器，防止请求过载导致雪崩。
5. ip/用户每日可以访问的总量
6. 对长地址进行验证，防止脚本注入和SQL注入
7. 对存储数据进行加密防止脱库
8. 对存储数据按时进行备份

### 8. 性能优化

使用redis 作为缓存, 以及 kafka消息队列（非必需）

1. 缓存热点数据防止击穿。
2. 使用布隆过滤器防止缓存穿透
3. 黑名单以及限流器防止雪崩
4. 用户的token存放位置
5. 消息队列可以作为日志异步写入

### 9. 扩展性设计

1. 应用服务水平扩展: Docker容器化技术和K8s部署
2. 数据水平扩展：mongodb的分片
3. 缓存水平扩展：redis的哈希槽

### 10. 监控与日志

1. 全链路追踪：skywaling 和 traceId
2. 日志：Grafana 记录以及可视化
3. 监控到问题可以使用 短息或者微信通知给开发人员

## 三、测试

### 1.单元测试： ![单元测试]( https://floralatin-blog.oss-cn-beijing.aliyuncs.com/images/projects/domain/test_unit.png)

### 2.集成测试： ![集成测试]( https://floralatin-blog.oss-cn-beijing.aliyuncs.com/images/projects/domain/test_integration.png)

##### (1)生成短地址的用例测试

|  用例   | 输入 | 输出 |  预期结果   | 
|  --------  | --------  | ----  |  ----  | 
| 不能生成短地址 | 1.正常的Url <br>2.header没有设置'Authentication' |  404, "Authentication token missing" | 失败 
| 不能生成短地址 | 1.正常的Url <br>2.header设置错误'Authentication' |  401, "Wrong authentication token" | 失败 
| 不能生成短地址 | 1.非正常的Url（带有脚本注入） <br>2.header设置正确'Authentication' |  400, "Invalid URL" |  失败 
| 不能生成短地址 | 1.没有url参数 <br>2.header设置正确'Authentication |  400, "Invalid URL" | 失败 
| 不能生成短地址 | 1.超级长(超过2082个字符)的的Url <br>2.header设置正确'Authentication' |   400, "Invalid URL" | 失败 
| 正常生成短地址 | 1.正常的Url <br>2.header设置正确'Authentication' | 200, "{url: http:127.0.0.1/wqemdawd}" | 成功生成短地址
| 正常生成短地址 | 1.同样的的Url <br>2.header设置正确'Authentication'  |  200, "{url: http:127.0.0.1/wqemdawd}"，返回已存在的短地址，不会重新生成 | 成功返回短地址
| 正常生成短地址 | 1.超级长(2083个字符以内)的的Url <br>2.header设置正确'Authentication'  |   200, "{url: http:127.0.0.1/sdadasd}"  | 成功生成短地址
| 正常生成短地址 | 1.统一的Url <br>2.header设置正确'Authentication', 使用两个不同的用户 |  生成两个同的短地址 |   生成两个同的短地址
| 正常生成短地址 | 1.超级长(2083个字符以内)的的Url <br>2.header设置正确'Authentication', 使用两个不同的用户,(第一个用户是黑名单)，第二用户不是黑名单 | 第一个用户访问返回 403，第二个成功生成短地址，200  | 第一个用户不能生成短地址，第二个用户成功生成短地址


##### (2)重定向长地址的用例测试

|  用例   | 输入 | 输出 |  预期结果   | 
|  ----  | --------  | ----  |  ----  | 
| 正常跳转到长地址 | 1.已经存在的短地址  |  302 重定向 | 正常跳转 |
| 正常跳转到长地址 | 1.同一个已经存在的短地址  |  302 重定向，从cache中获取 | 正常跳转 |
| 不正常跳转到长地址 | 1.不存在的短地址  |  404 'not found' | 不能正常跳转 |
| 不正常跳转到长地址 | 1.错误的短地址code |  400 'Invalid Code' | 不能正常跳转 |
| 不正常跳转到长地址 | 1.错误的短地址code长度超过8位 |  400 'Invalid Code' | 不能正常跳转 |
| 不正常跳转到长地址 | 1.正常短地址code， 2同一个IP地址 1 秒内请求10次以上 |  403 'Forbidden.' | 不能正常跳转 |
| 不正常跳转到长地址 | 1.正常短地址code， 2同一个IP地址一天内请求500次以上 |  429 'Too Many Requests' | 不能正常跳转 |


##  四、表设计

使用Mongodb来存储

**Url：**

|  列明   | 数据类型  | 索引  | 描述  |
|  ----  | ----  | ----  |  ----  |
| uid  | string | 主键 唯一 分片键 | 记录uid （雪花算法生成）|
| code  | string | Y |  短地址code码（0-8位） |
| url  | string | Y 哈希索引 | 原地址  |
| userUid  | string | Y | 用户uid  |
| expiredTime  | Date | N | 记录uid |
| createTime  | Date | N  | 创建时间 |
| updateTime  | Date | N  | 更新时间 |
| available  | Boolean|  N  | 逻辑删除键 |

**User：**

|  列明   | 数据类型  | 索引  | 描述  |
|  ----  | ----  | ----  |  ----  |
| uid  | string | 唯一，分片键 | 记录uid （雪花算法生成）|
| username  | string | 哈希索引，唯一 |  用户名 |
| password  | string | N | 密码  |
| salt  | string | N|  密码加盐  |
| createTime  | Date | N  | 创建时间 |
| updateTime  | Date | N  | 更新时间 |
| available  | Boolean|  N  | 逻辑删除键 |

**Statistics：**

|  列明   | 数据类型  | 索引  | 描述  |
|  ----  | ----  | ----  |  ----  |
| uid|  string | Y,唯一 | 记录 uid |
| urlUid  | string | Y | url的uid，并作为shardKey |
| ip  | string | N |  用户访问ip |
| referer  | string | N | 跳转的网页  |
| userAgent  | string | N|  客户端信息包括浏览器信息，os信息等  |
| language  | string | N|  用户使用的语言  |
| accept  | string | N|  客户端接受的信息配置 |
| createTime  | Date | N  | 创建时间 |
| available  | Boolean|  N  | 逻辑删除键 |
