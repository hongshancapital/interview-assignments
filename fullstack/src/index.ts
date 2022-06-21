import express from "express";
import { Storage } from "./model/index";
import { ResUtil, ShortUrlUtil } from "./util/index";

const Const_Host = "https://www.sequoiacap.cn";
const Const_Listen_Port = process.env.PORT || 3000;
const app = express();

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
app.get("/api/v1/set", (req, res, next) => {
  // 确保传入参数一定是string
  let targetUrl = `${req.query?.longUrl}`;
  if (targetUrl === "undefined") {
    res.json(ResUtil.showError({ msg: "未检测到url" }));
    // 明确指明程序到此终止, 增加可读性
    return;
  }
  if (ShortUrlUtil.isLegal(targetUrl) === false) {
    res.json(ResUtil.showError({ msg: "url不合法, 请检测后重试" }));
    return;
  }

  let key = Storage.add(targetUrl);

  res.json(
    ResUtil.showResult({
      data: `${Const_Host}/${key}`,
    })
  );
  return;
});

app.listen(Const_Listen_Port, () => {
  console.log("Server listening on port:", Const_Listen_Port);
});
