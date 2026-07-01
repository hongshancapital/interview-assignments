# DEMO 生成短链服务

功能说明

- 添加短链接.通过调用 API 接口，实现将复杂的 URL 转换成比较短的链接地址。（新增）
- 页面跳转.在上一步返回的短 url 在浏览器中输入，直接跳转至所配置的长链接地址。（查询、跳转）
- 编辑短链接。对上述映射关系可以编辑。（编辑）

如页面原始地址生成短链接：

```
https://www.163.com/sports/article/H7BARQL800058780.html
```

短链接地址：
```
http://localhost:3777/l/q98
```

# 工程结构

```
MyProject
├── app              // 项目代码
│   ├── bin          // 启动命令
│   ├── boots        // 启动初始化，数据库连接、redis连接、定时任务
│   ├── config       // 常量配置
│   │   └── mysql.ts // mysql配置
│   │   └── redis.ts // redis配置
│   │   └── base.ts  // 基础配置
│   ├── entity       // 放实体（数据库模型）的目录
│   │   └── App.ts   // 应用
│   │   └── Link.ts  // 短链
│   ├── dataCore     // 数据处理核心模块
│   │   └── hash.ts  // 计算短链的hash
│   │   └── redisClint.ts  // redis常用数据方法
│   │   └── slink.ts  // 计算短链入口
│   ├── descriptor    // 装饰器
│   │   └── router.ts  // 路由装饰器
│   │   └── service.ts  // 服务装饰器
│   ├── router    // 路由
│   │   └── linkRouter.ts  // 页面跳转
│   │   └── manRouter.ts  // 管理类Api接口
│   ├── service    // 服务
│   │   └── sLinkService.ts  // 短链相关服务
│   ├── test    //  单元测试
│   └── index.ts     // 应用程序入口
├── .gitignore       // 标准git忽略文件
├── package.json     // node模块依赖
├── README.md        // 简单的说明文件
└── tsconfig.json    // TypeScript编译配置
```

# 项目使用

## 1、安装

```
npm i
```

## 2、使用

```
npm run dev // 调试模式
npm run build // 打包
mocha ./test/slink.js  // 单测
npm run test // 单测, 需先 运行 npm run dev
```

## 3、 功能说明

### 3.1 设计文档

[设计文档](./doc/designe.md)

### 3.2 接口文档

[接口文档](./doc/api.md)

### 3.3 单元测试

```
 #Test Short Link
    #Add API /man/a
      ✔ appid is empty (54ms)
      ✔ oriUrl is empty
      ✔ Add Return slink (134ms)
    #Query API /man/q
      ✔ slink is empty
      ✔ slink non-existent (112ms)
      ✔ slink existent (109ms)
    #Update API /man/u
      ✔ slink is empty
      ✔ oriUrl is empty
      ✔ update oriUrl  (85ms)
      ✔ slink diff  (41ms)
      ✔ reverter oriUrl  (85ms)
    #Page direct API /l/:slink
      ✔ Page direct to baidu (813ms)
```
