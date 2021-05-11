
### 构建和运行

##### make
* make help 查看帮助
* make test 测试
* make build 生产jar包
* make start 启动java服务
* make stop 关闭java服务

##### 测试功能
* curl --location --request POST 'http://127.0.0.1:8080/s/' \
--form 'shortLinkKey="https://www.didi.com/"'
* curl --location --request GET 'http://127.0.0.1:8080/s/{shortlinkKey}'


### 功能需求
* 根据输入的url，转为短链接
* 提供两个API
    * 短域名存储接口：接受长域名信息，返回短域名信息
    * 短域名读取接口：接受短域名信息，返回长域名信息

#### 暂未考虑的范围
* 不包括用户相关设计
* 不进行用户身份校验
* 未包含安全相关处理
* 不包括数据跟踪分析
* 不考虑DevOps

#### 假设
* 同一long link 可以对应多个短链接，每个短链接的唯一
* 假设long link 最大长度100字节以内
* api需要快速响应，如<200ms
* 以100w日活用户为前提
* 读写比例在 100:1
* 每天10w写入量，1000w读
* 每月300w写入，每年3600w写入
* 每天用户活跃时间10小时，80%的流量在这10小时内发生
* 预估每小时写入量8000/h, write TPS 2~3/s
* 预估每小时读取量800000/h,read TPS 200~300/s
* 流量并不均衡，预估峰值为日常值的3～5倍，write TPS 10~35/s，read TPS 1000~1500/s
* 短链接有效期30天
* 假设部署在云环境



#### 设计思路
##### 核心模块设计
###### 短域名存储接口
* 短链生成规则
    * shortlinkKey = base62_encode(murmur3_128(uuid))
    * 每次请求，系统生成 uuid version1(Time-Based) 
        * 基于时间戳、随机数和机器MAC地址得到
        * 可保证分布式唯一id
    * 使用murmur3_128(uuid)得到一个hashcode(int)
    * 将hashcode 使用base62 [a-zA-Z0-9]，得到字符长度不超过8的短链编码
    
* Bloom过滤
    * hash方式存在碰撞的可能，使用bloomfilter进行过滤
    * 如存在重复hashcode，则重新生成

* 为什么不使用自增id类型避免hash碰撞？
    * pros
        * 全局唯一id，没有重复
    * cons
        * 自增型id base62编码后，code规律易被攻击
    * 为了防止攻击型爬取，采取hash散列的方式，以Bloom过滤来防止hash的碰撞
###### 短域名读取接口
    * key-value 读取，直接从jvm内存中读取hashmap
    * 命中则返回long url
    * 否则返回空
###### 通讯协议
    * HTTP REST 
        * pros
            * 易于理解、debug、cache
            * 松耦合，客户端与服务端，易于扩展
        * cons
            * 多版本时，对客户端不友好
        
###### 存储
    * 作业示例，内存存储，带有过期机制，设置30天
    * 作业示例内存容量1000w
    * hash结构，key为短链接，长度不超过8位的字符串，value为long url（100字节最大）
    * 缓存容量，30天有效短链接，400～500MB
    * bloom过滤，key:int,1亿key 约占用400MB
    * DB持久化设计
        * id 8字节
        * short_url_key 32字节
        * long_url 200字节
        * expire_at 8字节
        * create_at 4字节
        * 每1000w需要 2～3GB存储
        * 支持三年的扩展1.2亿，需要30GB存储

###### 部署
    * LB+ASG+EC2（两台保证HA+Fail over)

#### 未来扩展

##### 性能测试
    * 持续根据用户访问量的增长，来测试系统性能和负载情况
    * 找出系统瓶颈，负载/响应时间/资源利用率/吞吐量
    * 评估优化方案+成本 权衡
    * 迭代式优化

##### 使用缓存
    * 读多写少，写一次无update，适合缓存，expire 30天淘汰
    * 提高访问速度，缓解对db层的访问压力
    * Read API返回内容 可以在客户端缓存
    * 利用CDN缓存，减少Read API访问
    * 应用层缓存，减少
        * local cache
        * distribute cache
    * 数据库缓存
        * 利用数据库的查询cache，减少磁盘io

##### 接入层——负载均衡
    * RR策略
    * 云上环境使用托管LB服务，支持水平扩展
    
##### 应用层
    * 无状态，水平扩展
    * 用户日活增长，API可以拆分部署
        * 读写服务分离
        * 两个服务故障隔离
    * 设计弹性伸缩的能力
        * LB+ASG+EC2
        * 部署EKS，HPA
        
##### 缓存层
    * 短链缓存
        * local cache（LRU），适合热点数据
        * distribute cache，如 memcached or redis集群
        * 随着key的增长 distribute cache 需要设计扩容方案
    * bloom过滤器缓存，使用redis集群，保证分布式检验
        * 短链接失效同步清理bloomfilter cache
          
##### 存储层（NoSQL或MySQL）
    * key-value 结构，可以使用nosql数据库，每千万容量在2～3G，可以考虑redis集群 or DynamoDB
        * 缺乏统计分析能力
        * 可以与data warehouse结合或者mysql结合做数据分析
        * 可以设置key的expire time，来淘汰数据
        * 随着key的增长需要设计扩容方案
            * DynamoDB 云服务支持弹性扩展能力
    * RDS MySQL集群， 关系型数据库，稳定&数据分析能力，几乎无丢失数据的担忧
        * M-S架构，增加从库提高对读取的响应
        * 水平扩展成本高于nosql，如DynamoDB
        * 系统低峰期，定时清理过期数据，进行数据归档
        * 随着key的增长需要设计扩容方案
            * 水平分片 Hash-Based
    * 选择基于托管服务的Dynamodb，解决扩展问题
        * Dynamodb streaming + RDS/DW 解决统计分析问题
    * DB IO write 瓶颈时
        * write api对db压力过大时 write-back策略
            * 写distribute cache
            * 写队列
            * 异步消费写db
##### 部署支持
    * 支持多region 部署
    * 独立的应用服务+数据库+缓存





