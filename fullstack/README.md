# TypeScript Fullstack Engineer Assignment

### Typescript 实现短域名服务（细节可以百度/谷歌）

撰写两个 API 接口

- 短域名存储接口：接受长域名信息，返回短域名信息
- 短域名读取接口：接受短域名信息，返回长域名信息。

限制

- 短域名长度最大为 8 个字符（不含域名）

递交作业内容

1. 源代码
2. 单元测试代码以及单元测试覆盖率
3. API 集成测试案例以及测试结果
4. 简单的框架设计图，以及所有做的假设
5. 涉及的 SQL 或者 NoSQL 的 Schema，注意标注出 Primary key 和 Index 如果有。

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



### 启动
docker 启动
```
docker-compose up -d --build
```
本地
```
docker run -it --rm -p 27017:27017 mongo  
yarn build
yarn start
```
单元测试
```
yarn test
```

### 单元测试覆盖率
```
 PASS  src/tests/services/ShortUrlService.spec.ts
 PASS  src/tests/constollers/ShortUrlController.spec.ts
------------------------|---------|----------|---------|---------|-------------------
File                    | % Stmts | % Branch | % Funcs | % Lines | Uncovered Line #s 
------------------------|---------|----------|---------|---------|-------------------
All files               |     100 |      100 |     100 |     100 |                   
 controllers            |     100 |      100 |     100 |     100 |                   
  ShortUrlController.ts |     100 |      100 |     100 |     100 |                   
 models                 |     100 |      100 |     100 |     100 |                   
  ShortUrlModel.ts      |     100 |      100 |     100 |     100 |                   
 services               |     100 |      100 |     100 |     100 |                   
  ShortUrlService.ts    |     100 |      100 |     100 |     100 |                   
------------------------|---------|----------|---------|---------|-------------------

Test Suites: 2 passed, 2 total
Tests:       19 passed, 19 total
Snapshots:   0 total
Time:        1.545 s, estimated 2 s
Ran all test suites.
✨  Done in 2.34s.
```

### API 集成测试案例以及测试结果
1. 新建一个短域名
```
 curl --location --request POST 'http://localhost:3000/shorturl' -H 'Content-Type: application/json' -d '{"url": "https://www.baidu.com/new_url?a=1&n=2#123"}'              
{"url":"https://www.baidu.com/new_url?a=1&n=2#123","shortUrl":"https://www.baidu.com/00000001"}
```

2. 当一个域名已经存在短域名，返回这个短域名信息
```
 curl --location --request POST 'http://localhost:3000/shorturl' -H 'Content-Type: application/json' -d '{"url": "https://www.baidu.com/new_url?a=1&n=2#123"}'              
{"url":"https://www.baidu.com/new_url?a=1&n=2#123","shortUrl":"https://www.baidu.com/00000001"}
```

3. 输入已经创建好的短域名信息，返回该域名的原域名
```
curl --location --request GET 'http://localhost:3000/shorturl/00000001' -H 'Content-Type: application/json'
{"url":"https://www.baidu.com/new_url?a=1&n=2#123","shortUrl":"https://www.baidu.com/00000001"}%   
```

4. 输入一个无效的域名，返回错误
```
 curl --location --request POST 'http://localhost:3000/shorturl' -H 'Content-Type: application/json' -d '{"url": "https:///new_url?a=1&n=2#123"}' 
{"message":"url is invalid"}
```

5. 输入一个不存在的短域名信息，返回错误
```
curl --location --request GET 'http://localhost:3000/shorturl/00000009' -H 'Content-Type: application/json'
{"message":"shortId is invalid"}
```

6. 不传域名信息，返回错误
```
curl --location --request POST 'http://localhost:3000/shorturl' -H 'Content-Type: application/json' -d '{}'             
{"message":"url is not provided"}%  
```

### 设计思路以及简单的框架
对于这种简单的需要快速响应的服务，其实设计的时候首先想到的是用serverless服务 AWS lambda + dynamoDB 的框架。考虑到JD介绍里面的express + nodejs的结构，于是本次作业就用了 NodeJs + TypeScript + Express + MongoDB来完成的。 整体结构采用了 MVC + Rest API的方式实现。

项目主体框架
```
.
|-- server.ts // express server, entry point
|-- app.ts // application initilize and start
|-- routes
  |-- ShortUrlRoute
|-- controllers //controller
  |-- ShortUrlController
|-- models // model
  |-- ShortUrlModel
|-- services // services that used by ShortUrlController
  |-- ShortUrlService

```
REST API 两个

Request
```
GET /shortUrl/:shortId
```

Response
```
{
  "url": "string",
  "shortUrl": "string"
}
```

Request
```
POST /shortUrl
```
Request Body
```
{
  "url": "string, required",
}
```

Response
```
{
  "url": "string",
  "shortUrl": "string"
}
```

### 涉及到的schema
```
Document {
    url: string; //index, required, unique
    shortId: string; //index, required, unique
    shortUrl: string; //index, required, unique
    createdAt: Date; //auto generated
    updatedAt: Date; //auto generated
}
```

