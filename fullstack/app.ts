const express = require("express");
const app = express();
const createError = require("http-errors");
const bodyParser = require("body-parser");

import { ErrorMessage } from "./common/exception/exception-const";
import { logger } from "./common/logger";
import * as UrlApi from "./router/system/url";

// 服务器设置
app.use(bodyParser.json({ limit: "50mb" }));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

// node服务生命监测
app.use("*/ping", (req: any, res: any) => {
  res.send("pong");
});

// 注册接口
app.use("/url", UrlApi.router);

// 404
app.use(function (req: any, res: any, next: any) {
  next(createError(404));
});

// 错误处理
app.use(function (err: any, req: any, res: any, next: any) {
  if (err.name === "UnauthorizedError") {
    res.send(ErrorMessage.AUTH_LOGIN);
  }
  res.locals.message = err.message;
  res.locals.error = err;
  res.status(err.status || 500);
  res.send(err.message);
});

app.listen(8888, () => {
  logger.info("The app is listening on port 8888!");
});
