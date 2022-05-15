# TypeScript Fullstack Engineer Assignment

# 短域名服务设计文档

## HASH 函数

将长网址转为短网址，我们需要找到合适的 hash 函数，由于要求短网址的长度不超过8位，所以
常用的md5，sha1，sha128等不符合要求，常用的可以选择 CRC32，hex格式刚好8位，满足要求

同时在短网址服务常用的基于 62 进制的hash，可以将一个整数转换为 0-9a-zA-Z 组成的字符串

### CRC32

CRC32 结果为 8 位的 hex 格式，其能够表示的总数据量为 16^8,约 4.2 billion。同时，我们还需要处理 hash 冲突的情况。出现冲突后，需要在原字符串基础上拼接预设的字符串，再次 hash，继续判断 hash 是否冲突。

### Base 62 转换

Base 62 使用 0-9a-zA-Z 这 62 个字符，其能够表示的总数据量为 62^8 + 62^7 + ...+ 62^1，约 218 trillion。如果能够保证每个 url 对应的整数不充分，就能够保证转换结果不冲突。现代分布式系统中，一般都会有统一的 id 生成器来保证唯一 id。

经过比较，Base 62 转换能表示更大的数据量，同时处理的逻辑也相对简单。同时，将二进制数字转换为 62 进制字符串的过程是可逆的，所有可以将短网址转换为 id。至于 id 生成器，可以用数据库自增主键来实现，所以选择使用 Base 62 转换来作为转换函数。

## 缓存

查询数据库是一个耗时的操作，所以可以考虑使用缓存。为了保证服务的无状态性，选择使用 Redis 来作为服务的缓存。

## 请求流程图

longURL to shortURL

![longURL to shortURL](https://raw.githubusercontent.com/fearlessfe/image-bed/main/long.jpg)

shortURL to longURL

![shortURL to longURL](https://raw.githubusercontent.com/fearlessfe/image-bed/main/tempo_.jpg)

## 项目结构

项目采用 controller, service, dao 三层模式。数据库采用 mysql， 缓存使用 redis。

### 目录

* src
  * controller
  * service
  * store mysql服务
  * cache redis服务
  * utils 工具函数
  * types 类型定义

各模块之间依赖关系如下图

![dep](https://raw.githubusercontent.com/fearlessfe/image-bed/main/dep.jpg)

## 测试情况

测试覆盖率如下图

![cov](https://raw.githubusercontent.com/fearlessfe/image-bed/main/cov.png)