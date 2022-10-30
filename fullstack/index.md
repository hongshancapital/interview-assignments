# 短域名

## 设计要点

### 唯一性
采用 hashids + 数据库自增键生成短域名，32 位 int 范围内 hashid 生成的id最大为 7 个字符，数据库自增键保证短域名的唯一性。
### 读取
增加读取缓存
### 写入
增加 url hash 的索引,保证在写入时能快速查找是否已存在相同 url 的短域名

## 接口设计

## 数据库设计
 - id int incr
 - url varchar(1024)
 - url_hash binary(16) index (md5计算,可能存在碰撞,所以此值不设置唯一键)
 - create_time timestamp

## 框架设计

## TODO
 - [x] id生成
 - [x] 缓存
 - [x] 数据访问
 - [x] 控制器
 - [ ] App
 - [ ] 参数校验
 - [ ] 异常处理
 - [ ] 环境配置
