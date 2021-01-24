# 简介
这是一个满足题目中基础需求的版本，如果仅仅是“满足需求”的角度来说应该是足够使用的。

使用了 nanoid 生成随机的 8 位 ID，用 Redis 作为 K-V 数据库直接存储 id => url 的关联 （用 AOF 模式做持久化）。

# 延展
如果从基本的需求上延展下去，还可以实现以下几个比较实用的运营类功能：

- 统计地址的浏览量
- 统计访问来源（referrer）
- 提供直接可获得二维码的接口

由于时间有限未能在此实现，故在此介绍实现时可能会采用的方案。

## 数据库
因为需要记录额外的信息，所以不再简单的使用 Redis 来完成需求，计划使用 Mysql 来存储一些关联的数据，但 Redis 层仍然保留，作为缓存使用。
由于在 Mysql 侧有自增主键，可以考虑使用 [Hashids](https://hashids.org/) 作为对外显示 ID （网址上）的方案。

```
CREATE TABLE `urls` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `url` varchar(2048) NOT NULL,
  `viewCount` int(11) DEFAULT '0',
  `createdAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `referers` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `referrer` varchar(500) NOT NULL,
  `urlId` int(11) NOT NULL,
  `viewCount` int(11) DEFAULT '0',
  `createdAt` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

计划使用 Redis 的 INCR 记录短期内的访问数据，设置计划任务定期回写回 Mysql 做数据持久化。

当然，如果有比较精细的访问查询的需求，可以考虑不使用 MySQL，使用 Redis + 时序数据库 的方案来完成相关工作。

## 其他
当接口较多，比如加入用户系统等，当前的简单代码组织结构便不太适合于后续的开发和维护，会考虑使用 routing-controller，typedi 等库来优化代码结构，也可能根据实际需求选择一个集成度比较高的框架如 NestJS 等。

```
-------------------|---------|----------|---------|---------|-------------------
File               | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s
-------------------|---------|----------|---------|---------|-------------------
All files          |   92.71 |    59.26 |      85 |   92.47 |
 src               |   89.83 |       55 |   72.73 |   89.66 |
  index.spec.ts    |     100 |      100 |     100 |     100 |
  index.ts         |   81.82 |       55 |   57.14 |   81.25 | 48-58
  test.js          |     100 |      100 |     100 |     100 |
 src/utils         |    97.3 |    71.43 |     100 |   97.14 |
  Logger.ts        |     100 |       50 |     100 |     100 | 12
  ShortUrl.spec.ts |     100 |      100 |     100 |     100 |
  ShortUrl.ts      |      95 |       80 |     100 |   94.44 | 32
-------------------|---------|----------|---------|---------|-------------------
```

# TypeScript Fullstack Engineer Assignment

### Typescript 实现短域名服务（细节可以百度/谷歌）

撰写两个 API 接口

- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制：

- 短域名长度最大为 8 个字符

递交作业内容

1. 源代码
2. 单元测试代码以及单元测试覆盖率
3. API 集成测试案例以及测试结果
4. 简单的框架设计图，以及所有做的假设
5. 涉及的 SQL 或者 NoSQL 的 Schema，注意标注出 Primary key 和 Index 如果有。

## 岗位职责

- 根据产品交互稿构建高质量企业级 Web 应用
- 技术栈：Express + React
- 在产品迭代中逐步积累技术框架与组件库
- 根据业务需求适时地重构
- 为 Pull Request 提供有效的代码审查建议
- 设计并撰写固实的单元测试与集成测试

## 要求

- 三年以上技术相关工作经验
- 能高效并高质量交付产品
- 对业务逻辑有较为深刻的理解
- 加分项
  - 持续更新的技术博客
  - 长期维护的开源项目
  - 流畅阅读英文技术文档
  - 对审美有一定追求
  - 能力突出者可适当放宽年限
