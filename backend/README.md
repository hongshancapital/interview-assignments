# TypeScript Backend Engineer Assignment

### Typescript 实现短域名服务（细节可以百度/谷歌）

撰写两个 API 接口

- 短域名存储接口：接受长域名信息，返回短域名信息

POST /url/shortUrl

- 短域名读取接口：接受短域名信息，返回长域名信息。

POST /url/originUrl

限制

- 短域名长度最大为 8 个字符（不含域名）

递交作业内容

1. 源代码

见./short-url

2. 单元测试代码以及单元测试覆盖率(覆盖率请勿提交整个目录，一张图片或一个 text table 即可)

![test-cov.png]

3. API 集成测试案例以及测试结果
   
![test-cov.png]
或者运行: `npm run test:cov`

4. 简单的框架设计图，以及所有做的假设
   
- 设计思路
  - 在mysql中新建一个表, id自增, 其他字段包括originUrl(长链接),shortUrl(短链接)
  - 将originUrl插入数据, 获得一个id
  - 将id进行62进制转化获得一个字符串, 该字符串定义为shortName, 这个shortName加上域名即为链接
- 假设
  - 原始的originUrl不超过200个字符, 如果超过200也能实现但是本demo为实现, 需要了解可以微信沟通

5. 涉及的 SQL 或者 NoSQL 的 Schema，注意标注出 Primary key 和 Index 如果有。

请查看`backend/short-url/src/url/url.entity.ts`文件查看表设计, 其中originUrl设置唯一索引为了去重, shortUrl设置索引为了可以更快通过shortUrl获取originUrl.

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
