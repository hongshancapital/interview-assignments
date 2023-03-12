/*
 * @Author: zhangyan
 * @Date: 2023-03-09 01:52:54
 * @LastEditors: zhangyan
 * @LastEditTime: 2023-03-12 17:38:49
 * @FilePath: /fullstack/src/routes/getUrl.ts
 * @Description:  get_url接口，get请求，根据token返回相关url信息
 */

import express, { Request, Response } from "express";
import Db from "../plugin/db";
import dbconfig from "../../config/dbconfig";
import { ErrorType } from "../types/errorCode";
import { UrlInfo } from "../types/urlInfo";
import expressRedisCache from "express-redis-cache";

const router = express.Router();

// 增加redis缓存
const cache = expressRedisCache({ expire: 3600 });

router.get("/get_url", cache.route(), (req: Request, res: Response) => {
  const { token } = req.query;

  // token参数检查，缺少参数返回错误
  if (!token) {
    res.fail(ErrorType.miss_param);
  } else {
    // redis没有缓存则查询数据库，如果存在返回相关信息，没有返回错误
    Db.getInstance()
      .find(dbconfig.collectionName, { token: token })
      .then((data) => {
        if (data.length > 0) {
          let result: UrlInfo = {
            token: data[0].token,
            full_url: decodeURIComponent(data[0].full_url),
            create_time: data[0].create_time,
          };
          res.success(result);
        } else {
          res.fail(ErrorType.incorrect_token);
        }
      });
  }
});

export default router;
