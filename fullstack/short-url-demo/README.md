## 项目介绍

短域名开放服务

## 作者
鞠学健

## API

### 获取短域名
http://localhost:300/api/short?shortUrl=xxxxx

### 获取原始域名
http://localhost:300/api/origin?originUrl=xxxxx

## 单测
### 单测代码地址
/test

### 测试覆盖率
/coverage

## 文档
/doc

### API集成测试结果
/doc/APITestDoc

### 框架设计图
/doc/Diagram

### Redis键值定义
|Key|Value|
| ----------- | ----------- |
|short_url_hash_md5_key_\<UrlDigest\>	|<短域名地址>|
|short_url_hash_key_\<ShortUrl\>|	<原始域名地址>|
|short_url_serial_number|	自增序列号|