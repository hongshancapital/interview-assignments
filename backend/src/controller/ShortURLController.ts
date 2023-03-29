import { Request, Response } from "express";
import RespUtil from "../utils/RespUtil";
import snowflake from "../utils/Snowflake";
import { validationResult } from "express-validator";
import RedisService from "../service/RedisService";
import ShortService from "../service/ShortService";
import config from "../config/config";


export async function createShortURL(req: Request, res: Response) {
  try {
    const validRes = validationResult(req);
    if (!validRes.isEmpty()) {
      throw new Error(validRes.array()[0].msg)
    } else {
      const originalURL: string = (req.body.url as string).trim();
      let shortURL = await RedisService.get(originalURL);

      if (shortURL) {
        RespUtil.Success(res, config.BASE_HOST + shortURL);
        return;
      }

      shortURL = snowflake.snowflakeGenerator();
      let [short, _] = await ShortService.findOrCreate(shortURL, originalURL);
      const { original_url: key, short_url: value } = short;
      await RedisService.setex(key, value);
      RespUtil.Success(res, config.BASE_HOST + value);
    }
  } catch (err) {
    RespUtil.Fail(res);
  }
}

export async function getOriginalURL(req: Request, res: Response) {
  try {
    const validRes = validationResult(req);
    if (!validRes.isEmpty()) {
      throw new Error(validRes.array()[0].msg)
    } else {
      const shortURL: string = (req.params.url as string).trim();

      let original_url = await RedisService.get(shortURL);

      if (original_url) {
        RespUtil.Redirect(res, original_url);
        return;
      }

      let short = await ShortService.findByShortKey(shortURL);
      let originalUrl = short?.original_url;

      if (!originalUrl) {
        throw new Error("不存在该短链");
      }
      const {short_url: key, original_url: value} = short!;
      await RedisService.setex(key, value);
      RespUtil.Redirect(res, originalUrl);
    }
  } catch (err) {
    RespUtil.Fail(res);
  }
}