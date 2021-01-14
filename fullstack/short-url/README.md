# 短域名服务

基于 `express` 简单开发，依赖 `mysql` 和 `redis`，支持分布式部署。

## 数据库

启动服务时会自动同步表结构，可在配置文件中修改 `mysql.synchronize` 关闭，表结构定义详见 `src/modules/short-url/entities/short-url.entity.ts`

## API
### 获取短链接
`GET /:key`
### 创建短链接
`POST /create?url={url}`

## 安装

```bash
$ yarn install
```

## 启动服务

```bash
# development
$ yarn start
```

## 测试

```bash
# tests
$ yarn test

# tests with coverage
$ yarn test --coverage
```

## 测试结果

```
  PASS  src/modules/short-url/short-url.routes.test.ts (6.098 s)
  Short Url Endpoints Step 1
    ✓ [GetShortUrl] The short url is invalid: 111111111 (32 ms)
    ✓ [GetShortUrl] The short url is not found: ZZZZZZZZ (6 ms)
    ✓ [CreateShortUrl] The url is empty (3 ms)
    ✓ [CreateShortUrl] The url is invalid: invalidUrl (4 ms)
    ✓ [CreateShortUrl] The url is random: https://test.domain/1610609673048 (39 ms)
  Short Url Endpoints Step 2
    ✓ [CreateShortUrl] The url is duplicate: https://test.domain/1610609673048 (5 ms)
    ✓ [GetShortUrl] The short url is created (3 ms)

--------------------------------|---------|----------|---------|---------|--------------------------------
File                            | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s
--------------------------------|---------|----------|---------|---------|--------------------------------
All files                       |   90.06 |    76.67 |   95.24 |    90.2 |
 config                         |     100 |      100 |     100 |     100 |
  default.js                    |     100 |      100 |     100 |     100 |
  test.js                       |     100 |      100 |     100 |     100 |
 src                            |     100 |      100 |     100 |     100 |
  app.ts                        |     100 |      100 |     100 |     100 |
 src/core/enums                 |     100 |      100 |     100 |     100 |
  status-code.enum.ts           |     100 |      100 |     100 |     100 |
 src/libs                       |   93.75 |      100 |   83.33 |    96.3 |
  db.lib.ts                     |   90.91 |      100 |     100 |   88.89 | 12
  redis.lib.ts                  |   95.24 |      100 |      75 |     100 |
 src/modules/short-url          |   85.86 |       75 |     100 |   85.86 |
  short-url.module.ts           |     100 |      100 |     100 |     100 |
  short-url.routes.ts           |     100 |      100 |     100 |     100 |
  short-url.service.ts          |   81.82 |    73.08 |     100 |   81.82 | 68,73-75,89-90,111,122-134,152
 src/modules/short-url/entities |     100 |      100 |     100 |     100 |
  short-url.entity.ts           |     100 |      100 |     100 |     100 |
--------------------------------|---------|----------|---------|---------|--------------------------------
Test Suites: 1 passed, 1 total
Tests:       7 passed, 7 total
Snapshots:   0 total
Time:        6.246 s
Ran all test suites.
Done in 7.17s.
```

## 获取URL压力测试结果

```
# run ab test
$ ab -c 1000 -n 10000 http://127.0.0.1:3000/1

Document Path:          /1
Document Length:        79 bytes

Concurrency Level:      1000
Time taken for tests:   3.351 seconds
Complete requests:      10000
Failed requests:        0
Write errors:           0
Total transferred:      2860000 bytes
HTML transferred:       790000 bytes
Requests per second:    2984.14 [#/sec] (mean)
Time per request:       335.105 [ms] (mean)
Time per request:       0.335 [ms] (mean, across all concurrent requests)
Transfer rate:          833.46 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   93 308.2      6    3009
Processing:    45  145  94.3    128     955
Waiting:       13  117  92.1    102     919
Total:         46  237 325.1    135    3163

Percentage of the requests served within a certain time (ms)
  50%    135
  66%    143
  75%    151
  80%    156
  90%    534
  95%   1149
  98%   1172
  99%   1176
 100%   3163 (longest request)
```
