# 短域名服务作业

## 内容说明
此目录包含源代码，项目配置文件及单元测试代码。
 - 源代码在src目录下
 - 单元测试代码在src/test目录下

 运行方法：
  - 安装依赖：```yarn```
  - 运行服务：```yarn start```
  - 单元测试：```yarn test```

## 单元测试结果

```
$ yarn test
yarn run v1.22.19
$ rm -rf target/*; jest --coverage
 PASS  src/test/util.test.ts
 PASS  src/test/db.test.ts
 PASS  src/test/short_url_service.test.ts
----------------------|---------|----------|---------|---------|-------------------
File                  | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s 
----------------------|---------|----------|---------|---------|-------------------
All files             |     100 |      100 |     100 |     100 |                   
 db.ts                |     100 |      100 |     100 |     100 |                   
 short_url_service.ts |     100 |      100 |     100 |     100 |                   
 util.ts              |     100 |      100 |     100 |     100 |                   
----------------------|---------|----------|---------|---------|-------------------

Test Suites: 3 passed, 3 total
Tests:       15 passed, 15 total
Snapshots:   0 total
Time:        1.961 s
Ran all test suites.
Done in 2.76s.
```

## 集成测试案例及结果
首先启动服务，然后用postman 发送请求测试接口。

### 短域名存储接口
POST http://localhost:3000/
Body: {
    "url": "www.xxx.com"
}

  1. 第一次发送域名"www.xxx.com", 结果返回短网址“Q”
  2. 重复发送域名"www.xxx.com", 结果仍然返回相同的短网址“Q”
  3. 发送另一个域名"www.yyy.com", 结果返回短网址“W”

### 短域名读取接口
GET http://localhost:3000/<short URL>

  1. 请求查询已存在的短域名“Q”，返回原网址“www.xxx.com”
  2. 请求查询已存在的短域名“W”，返回原网址“www.yyy.com”
  3. 请求查询不存在的短域名“nn”，返回http 404
  4. 请求查询非法短域名“Q!”，返回http 400

## 设计及假设

### 假设
  - 假设插入请求压力没有大到性能敏感的程度，在避免重复和极致的插入性能之间可以选择避免重复
  - 假设短域名数量没有超过8个字符能表示的所有数量
  - 假设不需要有效期机制
  - 假设读取性能要求也没有高到数据库不能满足需要而必须使用缓存
  - 假设最多8个字符的短网址数量大约在千万级，不需要分库分表

### 设计
因为这里的设计比较简单，没有复杂逻辑只有一个service提供两个接口，存储用一个数据库表，所以简单表示一下：
```
客户端 -> 服务器 ->数据库
```
设计要点：
  - 数据库表中记录url的md5的值（加索引）来快速判断重复，如果实际情况可以不考虑去重也可以去掉这个字段。
  - 万一不同url的md5的值重复（实际md5碰撞的概率很低）可以给url末尾加空格再试。预设最多重试3次。
  - 生成的短网址使用数据库记录id（主键，默认索引），为缩短长度使用62进制（大小写字母+数字）

## 数据库设计
使用mysql数据库，innodb引擎，只有一张表。
### 短网址表shorturl
3列：
  - id，自增，主键，默认索引
  - hashcode，存放url md5的值，加索引用于快速判重
  - url，存放原始url

```SQL
CREATE TABLE `shorturl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `hashcode` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `url` varchar(2048) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `shorturl_hashcode_IDX` (`hashcode`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```