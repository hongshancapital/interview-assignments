# SURL

## Author

* ascmix@protonmail.com

## 需求分析和取舍

### 相同长链接是否要对应到同一个短链接?

* 本工程不存储长链接->短链接的对应关系, 每次请求的短链接都是新的

### 鉴权&统计

* 本工程不考虑

### 有一定有效期的短链接如何失效

* 数据库中记录expireAt, 鉴于短链接的业务特性, 可以考虑访问时再做标记删除, 而不需要定期的清理. 本工程未支持有效期.

## 数据库设计

### Link表

* shortUrl: 主键, text
* originalUrl: text

### 数据库选型

短链接只需要一个kv的存储(短链接->长链接), 没有一致性问题, 当DB的写入成为瓶颈时, 可以选择分布式KV数据库. 本工程使用Cassandra.

## 短链接生成

### 方案一: 分布式发号器

DB中存储当前的Id, 由一系列发号器, 每次从DB中获取x个quota(将DB中的Id加上x), 缓存在内存中, 响应请求返回id. 横向扩展十分方便, 每次的quota可以分配很多, DB上基本不会有压力.

### 方案二: snowflake

根据当前时间戳和机器id生成64位id, 横向扩展也十分方便, 且无需额外存储, 实现十分方便. 缺点是生成的id太大, 对应的短链接较长. 本工程使用此方案

## 高可用/高并发

* kubernetes为无状态服务提供高可用
* redis: 初步认为短链接有海量的读, 于是将热点链接放入redis. 并发要求更高时考虑搭建redis集群
* DB: Cassandra可以方便的scale out

## CI/CD

* CI: 使用gradle可以很方便的完成lint, compile, test
* CD: 通过git tag触发gradle中docker task, 自动完成docker image构建, 同时自动提交到deploy工程, 使用k8s进行rolling update

## 日志、监控和报警

* 日志: 使用业界通用的EFK + Kibana
* 监控和报警: 使用业界通用的Prometheus + Grafana
* 本工程未实现(给DevOps同学们一点空间)