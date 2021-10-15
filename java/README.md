# 作业简单说明

## 基本思路

- 通过AtomicLong自增获取ID并转62进制数（0-9a-zA-z）生成短码。短码长度可配置。
- 实现LRU缓存保存短码-原始URL，并同步保存原始URL-短码避免重复消耗短码。缓存大小可配置。

## 系统设定

- 原始URL长度不超过2083，需要符合URL格式规范

## 测试说明

- 性能测试使用jmeter完成，测试坏境为1000线程同时请求，重复10次

##其他

- swagger地址：http://localhost:8080/swagger-ui.html#/short45url45controller







