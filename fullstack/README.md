# 秦高梯- fullstack 交付作业

### Development

```bash
$ cd fullstack
$ yarn install
$ npm run build
$ npm run start
$ open http://localhost:3000/

$ npm run dev  前后端调式模式. 第一次使用可能需要多等会,生成前端,后端打包文件.有报错不急.
$ npm run test 跑覆盖率
```

## 交付描述

1.源代码  

技术栈：Express + React + bootstrap + Mongoose

src - client 前端代码
         组件核心 app.tsx, index.tsx
         头部组件 header.tsx
         链接输入框组件 inputByApi.tsx

src - server 后台代码
         common 通用东西
         controller 路由控制器层
         models 表
         service 服务层

打包前端： public 文件里的js
打包后台:  dist server.js

--------------------
2.单元测试代码以及单元测试覆盖率

3.API 集成测试案例以及测试结果

npm run test 跑覆盖率+单元集成测试， 另有截图在doc文件

-------------------------------------------

4.简单的框架设计图，以及所有做的假设

在./doc文件里面有架构图和假设

--------------------------------

5.涉及的 SQL 或者 NoSQL 的 Schema，注意标注出 Primary key 和 Index 如果有。

在./src/server/models 

--------------------------------


### Requirement

- Node.js 16.x
- Typescript 4.1+
