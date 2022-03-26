# 秦高梯- fullstack 交付作业

[gaoTi News](https://github.com/qingaoti) showcase using typescript && egg

## 交付描述

1.源代码  
--   使用eggjs + typejs + mongo
--------------------
2.单元测试代码以及单元测试覆盖率
-- 可以用npm run cov 跑覆盖率， 另外有截图在doc文件夹里。
--------------------------
3.API 集成测试案例以及测试结果
-- 使用swagger-ui 跑的集成测试，另外有截图在doc文件夹里。
-------------------------------------------
4.简单的框架设计图，以及所有做的假设
-- 在架构设计图里。
---------------------------------
5.涉及的 SQL 或者 NoSQL 的 Schema，注意标注出 Primary key 和 Index 如果有。
-- 在架构设计图里。
--------------------------------
File directory /doc . Screenshots of submitted jobs


### Development

```bash
$ npm i
$ npm run dev
$ open http://localhost:7001/
```

Don't tsc compile at development mode, if you had run `tsc` then you need to `npm run clean` before `npm run dev`.

### Deploy

```bash
$ npm run tsc
$ npm start
```

### Npm Scripts

- Use `npm run lint` to check code style
- Use `npm test` to run unit test
- se `npm run clean` to clean compiled js at development mode once

### Requirement

- Node.js 8.x
- Typescript 2.8+
