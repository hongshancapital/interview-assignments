import { ShortId } from "../utils/id";
import express from "express";
import murmurhash from "murmurhash";
import db from "../db";

const linkRouter = express.Router();

// 短域名
const shortHost = "http://localhost:8000";
// 过期时间，可以做成运营后台配置
const expireTimeLen = 2 * 365 * 24 * 3600;

// 参数是传入一个机器码，实际项目中根据部署环境计算一个 实例 workId
const shortIdObj = new ShortId(19);

/**
 * 该接口需要前置鉴权
 */
linkRouter.post("/open-api/slink", (req, res) => {
  console.dir("Post /slink", req.body);
  // 参数检查
  const errors: string[] = [];
  if (!req.body.lurl) {
    errors.push("please input lurl(long url)");
  }
  if (!req.body.app_id) {
    errors.push("please input app_id");
  }

  if (errors.length) {
    res.status(400).json({ error: errors.join(",") });
    return;
  }

  // 生成 shortId
  const shortId = shortIdObj.generateShortId(req.body.lurl);

  // 存储
  const sql =
    "INSERT INTO cloud_short_link (lurl, short_id, app_id, expire_time, create_time) VALUES (?,?,?,?,?)";
  db.run(
    sql,
    [
      req.body.lurl,
      shortId,
      req.body.app_id,
      new Date().getTime() / 1000 + expireTimeLen,
      new Date().getTime() / 1000,
    ],
    (err: Error) => {
      if (err) {
        console.dir(err);
        res
          .status(400)
          .json({ error: `url had exists, shortUrl: ${shortHost}/${shortId}` });
        return;
      }
      res.json({
        message: "success",
        code: 0,
        data: {
          slink: `${shortHost}/${shortId}`,
        },
      });
    }
  );

  // todo 待优化，当日活很大时，需要进行进行哈希碰撞处理，前期不需要
});

/**
 * 短域名重定向接口
 */
linkRouter.get("/:shortId", (req, res) => {
  const shortId = req.params.shortId;
  console.dir(`Get /${shortId}`);

  if (shortId.length > 8) {
    res.status(400).json({ error: "shortId is err" });
    return;
  }

  const sql = "select lurl from cloud_short_link where short_id = ?";
  db.all(sql, [shortId], (err: Error, rows: any[]) => {
    if (rows.length == 0 || err) {
      res.status(404);
      return;
    }

    // 302 永久重定向
    res.redirect(302, rows[0].lurl);
  });
});

export default linkRouter;
