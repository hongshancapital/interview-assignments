# short-chain-service

## 简介

该项目为短链服务，提供根据原始链接生成短链接和根据短链接302跳转到原始链接的服务

项目采用的是 **egg + typescript + mysql + sequelize** 开发方式。

## 架构图

![architecture](https://raw.githubusercontent.com/weiruifeng/img-folder/main/short-chain-service/architecture.png)

该项目采用了多进程模式来保证 cpu 资源能够更加充分的利用，缓存使用了本地内存 + Redis 两层缓存，本地内存采用了LRU策略，主要是为了降低 Redis 负载。本地内存实现了多进程之间同步更新。

## 生成短链

![tinyUrl](https://raw.githubusercontent.com/weiruifeng/img-folder/main/short-chain-service/tiny-url.png)

* 布隆过滤器用于判断长链接是否已经存在
* ID获取采用的是获取存储表的最大ID，速度非常快
* 生成短链采用了使用ID + 添加噪声后再转换为62进制字符串，不足8位补全8位的操作
  
## 短链跳转

![originalUrl](https://raw.githubusercontent.com/weiruifeng/img-folder/main/short-chain-service/original-url.png)

* 获取数据的优先级为本地内存，redis缓存，数据库
* 由于活跃的短链占比较小，缓存写入采用了读时缓存，读时缓存占比较小，且命中率高

## 定时任务
![schedule](https://raw.githubusercontent.com/weiruifeng/img-folder/main/short-chain-service/schedule.png)

定时任务定期的将过期的短链删除，并更新redis缓存和本地内存。

## 单元测试
![schedule](https://raw.githubusercontent.com/weiruifeng/img-folder/main/short-chain-service/unit-test.png)
