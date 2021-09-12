# TypeScript Fullstack Engineer Assignment

> Disclaimer: When I started this assignment, I had little knowledge around typescript and express, it is built with a few hours learning during a weekend. As much as I enjoyed learning this new tech stack, I am only touching the surface wit limited time, this implementation is far from production ready.

### Submission Description
Only source code are included in this pull request, files like `package.json`, `tsconfig.json` etc are excluded to make it easier for reviewing.
There are some assumptions made in this implementation:
1. I'm using in memory data structures to mimic a database behavior. I assume setting up a database locally is not important in the context of this assignment.
2. We won't run out of short urls with 8 chars. Since I am using a base 64 conversions, 8 chars short urls could cover 2^48 long urls(around 1000 trillion urls), which should be enough for most of the applications.
3. I didn't include anything related to security, things like rate limiting is out of the scope of this assignment
4. Users/sessions are not include in this implementation.



### High Level System Diagram

This is the system diagram from 1000 feet view, there are 3 layers in the system, a controller layer, a service layer and a storage layer

Client

++++++++++++

Controller Layer

++++++++++++

Service Layer

++++++++++++

Storage Layer

++++++++++++

#### Controller Layer
Two API are defined in app.ts and connected to two top level functions in shortener_controller.ts. Detailed information about the APIs are discussed in API interfaces section below. 

Implementation of this layer lives in /controllers/shortener_controller.ts

#### Service Layer
Business logic around how to convert between short url and long url lives in this layer. I am using a base64 conversion approach for this assignment. The algorithm of converting long url to short url works as such: we maintain an auto increment global ID. When a new long url provided, ID will be increased and converted to a base64 string. This string will be part of the short url. At the same time, we maintain a mapping between ID and long url. 
When we want to extract long url from short url, we convert the short url to an ID, and look up the corresponding long url in our mapping. 

Implementation of this layer lives in /services/shortener_service.ts

#### Storage Layer
Storage layer is mainly used to maintain the mapping between global ID and long url, For the purpose of this assignment, I used data structure Maps. If we want to productionize this, we would need a persistent storage, a No SQL key value store would serve the purpose well. We can even put a in memory cache layer in front of the database layer to improve performance.

Please see the following DB schema(with primary key and index annotated)

| id (primary key) | longUrl(index) | created_at | other meta fields ... |

Implementation of this layer lives in /storage/url_storage.ts



### API interfaces

#### Generate short URL
**Request**

`POST /shorten { data: http://long-url.com }`

**Response**

```
{
    "data": "http://go/aO8ILeO2"
}
```

#### Get long URL
**Request**

`GET /go/aO8ILeO2`

**Response**

```
{
  "data": "http://long-url.com"
}
```



### Test Plans

**Unit Tests with Coverage**

>90% test coverage is a good enough metrics. It should be 100% for my implementation, I think there can be some improvements for the test coverage library I am using, for example the test/intergation_test.ts shouldn't be counted)

|filenames (7)                                | percent (97.41%) | total (463) | covered (451) | uncovered (12) |
| ------------ | ------------ | ------------ | ------------ | ------------ |
|src/storage/url_storage.ts                   |          100.00% |          35 |            35 |              0 |
| src/services/shortener_service.ts           |          100.00% |          75 |            75 |              0 |
| src/controllers/shortener_controller.ts     |           94.20% |          69 |            65 |              4 |
| src/app.ts                                  |          100.00% |          27 |            27 |              0 |
| src/test/integration_test.ts                |           92.86% |         112 |           104 |              8 |
| src/test/services/shortener_service_test.ts |          100.00% |         105 |           105 |              0 |
| src/test/storage/url_storage_test.ts        |          100.00% |          48 |            48 |              0 |


**Integration Tests**

There are also integration tests written in `test/integeration_test.ts` which tested the functionality end to end.

**Manual Testing**

I also did some manual testing by starting the server on localhost and use Postman to do some /GET and /POST requests to test against the APIs.


END
---

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
