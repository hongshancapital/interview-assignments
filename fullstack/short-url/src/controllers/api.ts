'use strict';

import {Response, Request} from 'express';
import {validateUrl} from '../util/url';
import {invalidURL} from '../const';
import {
  getOriginUrlById,
  genShortId,
  getKeyByOriginUrl,
} from '../services/shorturl';
/**
 * generate short url api.
 * @route GET /shorturl
 */
export const genShortUrl = async (req: Request, res: Response) => {
  const url: string = req.query['url'] as string;
  if (!validateUrl(url)) {
    return res.end(invalidURL);
  }

  let key: string = (await getKeyByOriginUrl(url)) as string;
  if (!key) key = await genShortId(url);
  // console.log(key)
  res.end(key);
};

/**
 * get short url api.
 * @route GET /shorturl
 */
export const getOriginUrl = async (req: Request, res: Response) => {
  const key = req.query['key'] as string;
  if (!key) return res.status(400).end();
  const value = await getOriginUrlById(key);
  res.end(value);
};
