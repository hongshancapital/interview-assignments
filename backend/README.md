一：整体设计思路：
1. 长短链接的参照关系，存入mysql
2. 为防止刷接口，对短域名进行Base62编码，并加入校验位
3. 为提升服务端性能，减少mysql qps，引入redis层

二：框架设计图及假设
2.1 框架
* model: 存储一些db相关的内容，包括mysql及redis
* service: 存储一些算法相关的内容，如编码函数
* src: 服务入口 app.ts在其中
* test: 测试脚本
2.2 假设
* 判断该服务运行时，场景为读多写少

三：mysql domainName表索引设计
1. id: 自增，主键
2. longName: 索引

四：单元测试及接口集成测试结果

> short-domain-names-service@1.0.0 test
> jest --detectOpenHandles --coverage

ts-jest[versions] (WARN) Version 5.0.2 of typescript installed has not been tested with ts-jest. If you're experiencing issues, consider using a supported version (>=4.3.0 <5.0.0-0). Please do not report issues in ts-jest if you are using unsupported versions.
 PASS  test/api.spec.ts
 PASS  test/mysql.spec.ts
 PASS  test/codeUtil.spec.ts
--------------|---------|----------|---------|---------|-------------------
File          | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s 
--------------|---------|----------|---------|---------|-------------------
All files     |   98.13 |    92.59 |     100 |   98.07 |                   
 model        |     100 |      100 |     100 |     100 |                   
  mysql.ts    |     100 |      100 |     100 |     100 |                   
  redis.ts    |     100 |      100 |     100 |     100 |                   
 service      |     100 |      100 |     100 |     100 |                   
  codeUtil.ts |     100 |      100 |     100 |     100 |                   
 src          |   96.15 |    83.33 |     100 |      96 |                   
  app.ts      |   96.15 |    83.33 |     100 |      96 | 30,48             
--------------|---------|----------|---------|---------|-------------------

Test Suites: 3 passed, 3 total
Tests:       23 passed, 23 total
Snapshots:   0 total
Time:        3.661 s
Ran all test suites.