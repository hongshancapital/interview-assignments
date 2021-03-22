"use strict";

import * as cache from "../db/cache";
import LevelDb from "../db/db";
import { convertToShortId } from "../util/url";
import { notFound, shortUrlKey } from "../const";
import logger from "../util/logger";

/** 
 * generate short id for url and save url to DB.
 *
 * @param url the input origin URL
 */
export const genShortId = async (url: string) => {
  // get a new integer key
  let newId: number = (await cache.getNewId()) as number;
  // convert the integer to base64 short key
  let shortId: string = convertToShortId(newId);

  logger.info(`Generated New Id is ${newId}, New key is ${shortId}`);

  // save data to DB
  await LevelDb.setId(newId);
  await LevelDb.setUrl(shortId, url);

  return shortId;
};


/**
 * get origin url from cahce or db.
 * 
 * @param key base64 short key
 */
export const getOriginUrlById = async (key: string) => {
  // get origin URL from cache
  let url: string = (await cache.get(key)) as string;
  if (!url) {
    try {
      // get origin URL from DB if url is not in cache
      url = await LevelDb.getOriginUrl(key);
      // put origin URL to cache
      cache.set(key, url);

      logger.info(`Get origin URL ${url}, key is ${key}`);

      return url;
    } catch (e) {
      logger.error(`ERROR occurred when getting origin URL ${e}, key is ${key}`);
      return notFound;
    }
  } else {
    return url;
  }
};

/**
 * reset cahce and db to 0.
 *
 */
export const resetData = async () => {
  await LevelDb.setId(0);
  cache.set(shortUrlKey, "0");
};
