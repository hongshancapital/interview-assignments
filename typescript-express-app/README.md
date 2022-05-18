## 短域名服务

## 目录结构

```
├── README.md                        // 简要说明
├── config                           // 配置文件夹
│   ├── db.ts                        // MongoDB数据库配置
│   └── default.json                 // 默认链接配置
├── package-lock.json
├── package.json
├── api                              // API层
│   ├── urlLongToShort.ts            // 长域名转换成短域名接口
│   └── urlShortToLong.ts            // 短域名重定向长域名接口
│  
├── models                           // 数据库操作
│   └── url.ts                       // NoSQL的Schema配置
├── others                           // 附件包
│   ├── 简单框架设计图.png             // 简单框架设计图
│   └── 单元测试及测试覆盖率.png        // Jest单元测试及测试覆盖率截图
├── routes                           // 路由层
│   └── index.ts                     // 统一的路由分发配置
├── utils                            // 工具包
│   └── tool.ts                      // 统一的工具函数集中定义
├── app.ts                           // 服务入口文件
├── babel.config.ts                  // babel配置文件
├── jest.config.ts                   // Jest测试配置文件
├── testServer.ts                    // API接口测试服务，需要使用Supertest
└── unit.test.ts                     // Jest单元测试文件
```

## 启服务

```shell
# 本地开发调试启动命令，会用到nodemon
npm run dev

# 本地Jest单元测试启动命令，会伴随生成单元测试覆盖率报告
npm run test

# 启动线上服务命令
npm run server

# 编译TS至JS到build文件目录命令
npm run build

# 清空编译后的文件命令
npm run clean

```
