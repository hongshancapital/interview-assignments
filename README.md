# 王有朋

Koa2 + MongoDB + TypeScript

备注：作业说明详细见 ./doc 文件


## Get Started

> Please make sure the MongoDB service is running before starting the app!

## 启动前准备 配置文件：
```js
{
  "dbUrl": "mongodb+srv://<user>:<password>@<cluster>.bqgte.mongodb.net/<dbname>?retryWrites=true&w=majority"
  // ,"jwtSecret": "your-secret-word"
}
```

## 启动
```bash
$ npm install
$ npm start
```

## API 集成测试案例
存储接口 req：
```bash
curl --location --request POST 'http://localhost:3300/api/urls' \
--header 'Content-Type: application/json' \
--data-raw '{
    "url":"https://github.com/scdt-china/interview-assignments/tree/master/backend"
}'
```
存储接口 res：

```bash
{"code":200,"msg":"success","data":{"shorturl":"www.z.cn/a"}}
```

读取接口 req： 浏览器打开：
```bash
http://localhost:3300/api/urls?shorturl=www.z.cn/a
```

res:
```bash
{"code":200,"msg":"success","data":"https://github.com/scdt-china/interview-assignments/tree/master/backend"}
```

