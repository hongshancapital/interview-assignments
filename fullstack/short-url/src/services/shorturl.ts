"use strict";

import * as cache from "../db/cache";
import LevelDb from "../db/db";
import { convertToShortId } from "../util/url";
import { notFound} from "../const";

/**
 * generate short id for url and save url to DB.
 * 
 */
export const genShortId = async (url:string) => {

  let newId: number = await cache.getNewId() as number;
  let shortId: string = convertToShortId(newId);
  
  await LevelDb.setId(newId);
  await LevelDb.setUrl(shortId, url);
  
  return shortId;
};

/**
 * get origin url from cahce or db.
 * 
 */
export const getOriginUrlById = async (key:string) => {
  let value: string = await cache.get(key) as string;
  if (!value) {
    try {
      value = await LevelDb.getOriginUrl(key);
      cache.set(key, value);
      return value;
    } catch (e) {
      return notFound;
    }
  }else{
    return value;
  }
};
