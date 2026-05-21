# 长短域名转换

## 测试覆盖率

```
 PASS  dist/test.js
  √ Get short URL (89 ms)
  √ Get same short URL (8 ms)
  √ Get different short URL (8 ms)
  √ Get long URL (6 ms)
  √ Get long URL but short URL no found (5 ms)
  √ Get different long URL (3 ms)
  √ Get long URL but do not pass a value (2 ms)

----------|---------|----------|---------|---------|-------------------
File      | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s 
----------|---------|----------|---------|---------|-------------------
All files |     100 |      100 |     100 |     100 |                   
 db.js    |     100 |      100 |     100 |     100 |                   
 index.js |     100 |      100 |     100 |     100 |                   
----------|---------|----------|---------|---------|-------------------
Test Suites: 1 passed, 1 total
Tests:       7 passed, 7 total
Snapshots:   0 total
Time:        1.919 s, estimated 2 s
```

## 整体设计

使用express框架提供服务

使用typescript进行编码，最终使用tsc编译成js运行

数据库连接使用mongoose，因为mongoose有骨架和更容易使用的model

测试类使用流行的jest来支撑

## 集成测试案例以及测试结果

### 短域名存储

```
curl --location --request POST 'http://localhost:3000/url' \
--header 'Content-Type: application/json' \
--data-raw '{
    "url": "www.baidu.com?name=zzw"
}'
```

第一次请求得到短码G6Hn_j2j

第二次请求，因为是相同的域名，所以依然返回短码G6Hn_j2j

第三次请求，传递不同的域名，返回新的短码9U5r1pHZ

### 短域名读取

```
curl --location --request GET 'http://localhost:3000/G6Hn_j2j'
```

请求查询已存在域名则返回对应的短码

请求查询不存在的域名则返回url 404

## 索引设计

因短码是唯一值，所以直接作为表的主键(_id)进行检索，可以减少不必要的字段存储

域名longUrl参数，索引为唯一索引，用于后续根据域名进行检索

## 假设

### 基础假设

传递的域名参数为空，需要返回invalid url提示客户端

传递的域名参数在后台不存在，则生成新的短码

传毒的域名参数在后台已存在，则返回已存在的短码

根据短码查询时，如果该短码在数据库中不存在，则返回url 404

根据短码查询时，如果该短码在数据库中存在，则返回对应短码

### 其他假设

因使用的是nanoId生成的短码，其随机性会因为生成频次和位数的变更而影响唯一性，若为核心短码，可以考虑使用分布式自增式短码解决

对重复的短码需要做容错处理，比如提示重新生成或后台自动生成

如果该业务是对外开放的服务，则需要做限流，针对ip，用户id或地域等级别做限流措施

如果该短码是临时性的，则可以设置过期时间，减少存储空间

如果需要对该业务数据进行分析，则还需要存储格外字段，比如用户信息，创建时间，ip等

目前该表只存储短码和域名，如果还存储了其他业务数据，则需要考虑分表
