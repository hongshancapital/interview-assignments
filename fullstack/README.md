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
3. API 集成测试案例以及测试结果
4. 简单的框架设计图，以及所有做的假设
5. 涉及的 SQL 或者 NoSQL 的 Schema，注意标注出 Primary key 和 Index 如果有。

### 代码介绍

1. 项目采用kos2+ts+nedb实现，源码包含几个文件夹
  service 数据服务的核心
  db nedb操作封装和数据库文件
  common 通用能力，可支持其他api共用
  types typescript定义
2. 单元测试代码在__test__下，采用jest实现
  单元测试覆盖率查看test-coverage.png文件  
3. 集成测试案例
  a. 短域名存储接口
  curl --location '127.0.0.1:3000/longtoshort?url=https%3A%2F%2Fwww.mt.com%2Faaa%2Fbbb%2Fccc%2Findex.html%3Fx%3D1%2523a%2F%3Ffoo%3D2'
  
  返回值
  {
    "code": 0,
    "data": {
        "url": "https://test.com/6232ac1e"
    }
  }

  b.短域名查询成功接口
   curl --location '127.0.0.1:3000/shorttolong?url=https%3A%2F%2Ftest.com%2F6232ac1e%3Fa%3D123'
   返回值
   {
    "code": 0,
    "data": {
        "url": "https://www.mt.com/aaa/bbb/ccc/index.html?x=1%23a/?foo=2"
    }
  }

  c.短域名查询为空
  curl --location '127.0.0.1:3000/shorttolong?url=https%3A%2F%2Ftest.com%2Fed489bd%3Fa%3D123'
  返回值
  {
    "code": -1,
    "msg": "{\"msg\":\"\\\"not found\\\"\"}"
  }

  d.输入的域名异常
  curl --location '127.0.0.1:3000/longtoshort?url=12333'
  返回值
  {
    "code": -1,
    "msg": "input is not a url"
  }

4. 流程图参考flow.png, 所有做的假设可以参考流程图
  架构图参考architecture.png

5. 采用NoSql,单条数据存储demo如下
{"key":"6232ac1e","sourceUrl":"https://www.mt.com/aaa/bbb/ccc/index.html?x=1%23a/?foo=2","_id":"tSAddLgOLDgQ8K63"}
Primary key是随机串_id

### 操作指南
1. yarn 
2. yarn dev 运行本地test环境
3. yarn test 执行单元测试


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
