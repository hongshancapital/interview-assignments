# 短域名服务

- 使用redis搭建的短域名服务
- 集成测试: 我放到自己的github上，跑通了就可以看了
    - github: https://github.com/SunGg12138/short-code-server

## 接口

- 重定向接口(GET /:shortCode)
- 生成短链(POST /api/shortCode)
- 获取短链信息(GET /api/shortCode)

## 启动

```shell
# 默认监听8000端口
npm start
```

## 测试&覆盖率

```shell
# 测试用例
npm test

# 覆盖率
npm run coverage
```

## 未来

- 参数校验层
- 生成短链接口需要做鉴权
- 生成短链接口可以设置更多参数
- 重定向接口以后还可以做ab测、灰度...
