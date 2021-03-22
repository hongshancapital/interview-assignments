"use strict";
/**
 * testing purpose
 */
import { Response, Request, NextFunction } from "express";
import { notFound } from "../const";
import { getOriginUrlById, resetData } from "../services/shorturl";
import logger from "../util/logger";

/**
 * redirect short url to origin url.
 * @route GET /:key
 */
export const redirectToOriginUrl = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  let key = req.path.substr(1);
  let value: string = await getOriginUrlById(key);

  if (value === notFound) {
    next();
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
    await resetData();
    res.end("0");
  } catch (e) {
    logger.error(`ERROR occurred when resetting data: ${e && e.message}`);
    res.end("reset error");
  }
};
