/*
 * @Author: zhangyan
 * @Date: 2023-03-09 01:53:02
 * @LastEditTime: 2023-03-12 18:17:30
 * @LastEditors: zhangyan
 * @FilePath: /fullstack/src/routes/setUrl.ts
 * @Description: set_url接口，post请求
 */

import express from "express";
import { genToken } from "../utils/genToken";
import Db from "../plugin/db";
import dbconfig from "../../config/dbconfig";
import { ErrorType } from "../types/errorCode";
import { UrlInfo } from "../types/urlInfo";
import { isValidHttpUrl } from "../utils/utils";

const router = express.Router();

// 检查token是否存在
const checkTokenExists = async (token: string): Promise<boolean> => {
  let result = false;

  await Db.getInstance()
    .count(dbconfig.collectionName, { token: token })
    .then((data) => {
      if (data > 0) {
        result = true;
      } else {
        result = false;
      }
    });

  return result;
};

router.post("/set_url", (req, res) => {
  const { url } = req.body;
  // 检查post参数是否存在
  if (!url) {
    res.fail(ErrorType.miss_param);
    return;
  }

  if (isValidHttpUrl(url)) {
    try {
      // 查询url是否已经存在，如果存在直接返回token信息
      // 可以入库的时候对url进行唯一编码，查询编码即可，demo演示这里直接查询url
      Db.getInstance()
        .find(dbconfig.collectionName, { full_url: encodeURIComponent(url) })
        .then(async (data) => {
          if (data.length > 0) {
            let result: UrlInfo = {
              token: data[0].token,
              full_url: decodeURIComponent(data[0].full_url),
              create_time: data[0].create_time,
            };
            res.success(result);
          } else {
            // 生成token，如果存在碰撞，重新生成，生成规则参考 genToken.ts
            // 这里仅作为demo简单演示，实际还有很多解决方案，参见 genToken.ts 注释
            let token = genToken();
            while ((await checkTokenExists(token)) == true) {
              token = genToken();
            }

            const create_time = new Date().getTime().toString();
            Db.getInstance()
              .insert(dbconfig.collectionName, {
                token: token,
                full_url: encodeURIComponent(url),
                create_time: create_time,
              })
              .then((succ) => {
                if (succ) {
                  let result: UrlInfo = {
                    token: token,
                    full_url: url,
                    create_time: create_time,
                  };
                  res.success(result);
                } else {
                  res.fail(ErrorType.add_url_faild);
                }
              });
          }
        });
    } catch (error) {
      res.fail(ErrorType.server_error);
    }
  } else {
    res.fail(ErrorType.incorrect_url);
  }
});

export default router;
