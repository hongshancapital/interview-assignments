import express from "express";
import { Storage } from "./model/index";
import { ResUtil } from "./util/index";

// 默认服务域名
const Const_Host = "https://www.sequoiacap.cn";
// 默认监听端口
const Const_Listen_Port = process.env.PORT || 3000;
// express实例
const app = express();

// 若文件代码超过100行, 则需要将逻辑代码迁移到router目录中

// 根据key获取url
app.get("/api/v1/get", (req, res, next) => {
  // 确保传入参数一定是string
  let targetKey = `${req.query?.key}`;

  let url = Storage.get(targetKey);
  if (url === undefined) {
    res.json(ResUtil.showError({ msg: "未找到对应url" }));
    // 明确指明程序到此终止, 增加可读性
    return;
  }

  res.json(ResUtil.showResult({ data: url }));
  return;
});

// 将url转为短网址key
app.get("/api/v1/storage", (req, res, next) => {
  // 确保传入参数一定是string
  // 不需要检测url是否合法, 直接储存即可.
  let targetUrl = `${req.query?.longUrl}`;
  if (targetUrl === "undefined") {
    res.json(ResUtil.showError({ msg: "未检测到url" }));
    return;
  }

  let key = Storage.add(targetUrl);

  res.json(
    ResUtil.showResult({
      data: `${key}`,
    })
  );
  return;
});

// app.listen(Const_Listen_Port, () => {
//   console.log(`Server listening on port: ${Const_Listen_Port}`);
// });

// 导出以供测试
export const ExpressApp = app;
