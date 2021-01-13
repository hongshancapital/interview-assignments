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
 PASS  src/modules/short-url/short-url.routes.test.ts (7.184 s)
  Short Url Endpoints Step 1
    ✓ [GetShortUrl] The short url is invalid: zzzzzzzzz (29 ms)
    ✓ [GetShortUrl] The short url is not found: zzzzzzzz (5 ms)
    ✓ [CreateShortUrl] The url is empty (3 ms)
    ✓ [CreateShortUrl] The url is invalid: invalidUrl (5 ms)
    ✓ [CreateShortUrl] The url is random: https://test.domain/1610508261775 (31 ms)
  Short Url Endpoints Step 2
    ✓ [CreateShortUrl] The url is duplicate: https://test.domain/1610508261775 (4 ms)
    ✓ [GetShortUrl] The short url is created (3 ms)

--------------------------------|---------|----------|---------|---------|----------------------------
File                            | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s
--------------------------------|---------|----------|---------|---------|----------------------------
All files                       |   91.03 |    76.67 |   95.24 |   91.22 |
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
 src/modules/short-url          |   87.37 |       75 |     100 |   87.37 |
  short-url.module.ts           |     100 |      100 |     100 |     100 |
  short-url.routes.ts           |     100 |      100 |     100 |     100 |
  short-url.service.ts          |   83.56 |    73.08 |     100 |   83.56 | 65,71-72,83-84,105,116-128
 src/modules/short-url/entities |     100 |      100 |     100 |     100 |
  short-url.entity.ts           |     100 |      100 |     100 |     100 |
--------------------------------|---------|----------|---------|---------|----------------------------
Test Suites: 1 passed, 1 total
Tests:       7 passed, 7 total
Snapshots:   0 total
Time:        7.325 s
Ran all test suites.
Done in 8.28s.
```

## 获取URL压力测试结果

```
# run ab test
$ ab -c 1000 -n 10000 http://127.0.0.1:3000/a

Concurrency Level:      1000
Time taken for tests:   3.093 seconds
Complete requests:      10000
Failed requests:        0
Write errors:           0
Total transferred:      2750000 bytes
HTML transferred:       680000 bytes
Requests per second:    3233.14 [#/sec] (mean)
Time per request:       309.297 [ms] (mean)
Time per request:       0.309 [ms] (mean, across all concurrent requests)
Transfer rate:          868.28 [Kbytes/sec] received
```
