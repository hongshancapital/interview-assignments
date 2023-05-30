# TypeScript Backend Engineer Assignment

### Typescript 实现短域名服务（细节可以百度/谷歌）

撰写两个 API 接口

- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

### 设计思路
1. 使用mysql的自增主键作为ID, 对ID做62进制编码, 将编码结果作为短域名的值
2. 为避免ID的碰撞, 因此这里将mysql事务的隔离等级设置为串行化
3. 用户输入长域名的长度未知, 因此在mysql中使用text进行存储, 并添加一个前缀索引


### API
- POST /insert (接受长域名信息，返回短域名信息)
- GET  /find (接受短域名信息，返回长域名信息)

### mysql
```
CREATE TABLE `t_url` (
  `id` int NOT NULL AUTO_INCREMENT,
  `short_url` varchar(8) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `long_url` text COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_short_url` (`short_url`),
  KEY `idx_long_url` (`long_url`(255)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci
```

### 测试结果

```
---------------------|---------|----------|---------|---------|-------------------
File                 | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s 
---------------------|---------|----------|---------|---------|-------------------
All files            |   96.48 |    89.47 |     100 |   96.48 |                   
 src                 |   95.18 |    94.11 |     100 |   95.18 |                   
  app.ts             |   91.66 |     87.5 |     100 |   91.66 | 50-57             
  config.ts          |     100 |      100 |     100 |     100 |                   
  router.ts          |     100 |      100 |     100 |     100 |                   
 src/controller      |   98.38 |    85.71 |     100 |   98.38 |                   
  base.ts            |     100 |      100 |     100 |     100 |                   
  short_url.ts       |      98 |    83.33 |     100 |      98 | 40                
 src/entity          |     100 |      100 |     100 |     100 |                   
  Url.ts             |     100 |      100 |     100 |     100 |                   
 src/middleware      |   95.95 |    81.81 |     100 |   95.95 |                   
  access_logger.ts   |     100 |      100 |     100 |     100 |                   
  error.ts           |     100 |      100 |     100 |     100 |                   
  response_handle.ts |      92 |    66.66 |     100 |      92 | 36-39             
 src/service         |   97.46 |     92.3 |     100 |   97.46 |                   
  base.ts            |     100 |      100 |     100 |     100 |                   
  short_url.ts       |   97.01 |     90.9 |     100 |   97.01 | 30-31             
---------------------|---------|----------|---------|---------|-------------------

Test Suites: 3 passed, 3 total
Tests:       7 passed, 7 total
Snapshots:   0 total
Time:        6.213 s
```