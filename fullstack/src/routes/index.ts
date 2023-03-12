/*
 * @Author: zhangyan
 * @Date: 2023-03-09 02:59:46
 * @LastEditors: zhangyan
 * @LastEditTime: 2023-03-12 17:38:30
 * @FilePath: /fullstack/src/routes/index.ts
 * @Description: 增加自定义succ和fail的功能实现
 */
import express, { Request } from "express";
import { Response } from "../types/response";
import Db from "../plugin/db";
import { errorCode, ErrorType } from "../types/errorCode";
import dbconfig from "../../config/dbconfig";
import { UrlInfo } from "../types/urlInfo";
import expressRedisCache from "express-redis-cache";

const router = express.Router();
// 增加redis缓存
const cache = expressRedisCache({ expire: 3600 });
// success 和 fail 的具体实现，主要统一返回的json格式
router.use((_req: Request, res: Response, next) => {
  res.success = (data): void => {
    res.json({
      error_code: errorCode[ErrorType.succ].code,
      message: errorCode[ErrorType.succ].msg,
      result: data,
    });
  };
  res.fail = (type: ErrorType, message?: string): void => {
    res.json({
      error_code: errorCode[type].code,
      message: message || errorCode[type].msg || "Unknow Error",
      result: {},
    });
  };
  next();
});

// 根据token，自动跳转目标网站，实现类似短网址功能 http://url/token/xxxxx
router.get("/token/:id", cache.route(), (req: Request, res: Response) => {
  var id = req.params.id;
  Db.getInstance()
    .find(dbconfig.collectionName, { token: id })
    .then((data) => {
      if (data.length > 0) {
        // 一般用302重定向可以用于统计，如无其他需要可以直接301更快
        res.redirect(301, decodeURIComponent((data[0] as UrlInfo).full_url));
      } else {
        // 没有查询到网址信息，返回一个错误信息
        res.send(errorCode[ErrorType.incorrect_token].msg);
      }
    });
});

export default router;
