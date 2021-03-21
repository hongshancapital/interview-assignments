"use strict";
/**
 * testing purpose
 */
import { Response, Request } from "express";
import * as cache from "../db/cache";
import db from "../db/db";
import { notFound,shortUrlKey } from "../const";

/**
 * redirect short url to origin url.
 * @route GET /:key
 */
export const redirectToOriginUrl = async (req: Request, res: Response) => {
  let key = req.path.substr(1);
  let value: string = (await cache.get(req.path)) as string;
  if (!value) {
    try {
      value = await db.getOriginUrl(key);
      cache.set(key, value);
      res.redirect(value);
    } catch (e) {
      res.end(notFound);
    }
  } else {
    res.redirect(value);
  }
};

/**
 * reset data.
 * @route GET /reset
 */
export const reset = async (req: Request, res: Response) => {
  try {
     await db.setId(0);
    cache.set(shortUrlKey, '0');
    res.end('0');
  } catch (e) {
    res.end(e.stack);
  }
};
