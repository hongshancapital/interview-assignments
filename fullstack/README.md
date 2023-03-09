# TypeScript Fullstack Engineer Assignment

### Typescript 实现短域名服务（细节可以百度/谷歌）

撰写两个 API 接口

- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制

- 短域名长度最大为 8 个字符（不含域名）

递交作业内容

1. 源代码
2. 单元测试代码以及单元测试覆盖率(覆盖率请勿提交整个目录，一张图片或一个 text table 即可)

单元测试源代码为`/src`目录中`.spec`结尾的文件。覆盖率：

| File              | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s |
|-------------------|---------|----------|---------|---------|-------------------|
| All files         | 90.69   | 100      | 89.65   | 90.06   |                   |
| src               | 42.85   | 100      | 25      | 38.46   |                   |
| app.module.ts     | 100     | 100      | 100     | 100     |                   |
| main.ts           | 0       | 100      | 0       | 0       | 1-25              |
| src/api           | 100     | 100      | 100     | 100     |                   |
| app.controller.ts | 100     | 100      | 100     | 100     |                   |
| input.ts          | 100     | 100      | 100     | 100     |                   |
| output.ts         | 100     | 100      | 100     | 100     |                   |
| src/db            | 100     | 100      | 100     | 100     |                   |
| db-data.ts        | 100     | 100      | 100     | 100     |                   |
| db.service.ts     | 100     | 100      | 100     | 100     |                   |
| url.entity.ts     | 100     | 100      | 100     | 100     |                   |
| src/service       | 100     | 100      | 100     | 100     |                   |
| app.service.ts    | 100     | 100      | 100     | 100     |                   |
| config.service.ts | 100     | 100      | 100     | 100     |                   |
| url.repository.ts | 100     | 100      | 100     | 100     |                   |

3. API 集成测试案例以及测试结果

案例详见`/test`中`.e2e-spec`结尾的文件，测试结果：
```
AppController (e2e)
  /getShortUrl (GET)
    ✓ should return short url (21 ms)
    ✓ should work with long url (5 ms)
    ✓ should return 400 with empty param (8 ms)
    ✓ should return 400 with invalid original url (5 ms)
    ✓ should return the same result with the same input (2 ms)
  /getOriginalUrl (GET)
    ✓ should return original url (3 ms)
    ✓ should return 400 with empty param (2 ms)
    ✓ should return 400 with invalid short url (2 ms)
    ✓ should return 400 with short url that not exist (2 ms)
```

4. 简单的框架设计图，以及所有做的假设
- 假设：
  - 10k左右QPS
  - 主要流量为由短链接获取长链接的请求
- 架构：nginx -> web server -> in-memory cache/db -> json file
- 设计：
  - 单部署，内存cache/db（固定间隔自动保存），json文件持久化
  - 从长链接sha1结果的前面四个字节计算出短链前两位字符，后接6位[0-9a-zA-Z]随机字符，兼顾长-短，短-长查找时的负载均衡和分表分库
  - 存储长链接的hash做索引优化查找
- 更高并发（未实现）：
  - 改用redis缓存+按短链接起始字符串分库分表
  - 按短链接起始字符串做负载均衡，使每个部署只连接单一分库
  - 创建和查询api分开部署
- 性能：i7-9750H@2.60GHz上单container QPS 11k（读写未见明显区别）左右
5. 涉及的 SQL 或者 NoSQL 的 Schema，注意标注出 Primary key 和 Index 如果有。

详见`/src/db/url.entity.ts`，`/src/db.json`

其他

- 我们期望不要过度设计，每一个依赖以及每一行代码都有足够充分的理由。

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
