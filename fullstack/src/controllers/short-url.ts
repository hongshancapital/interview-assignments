import express, { Request, Response } from 'express';
import LRU from 'lru-cache';
import {
  isValidBase62Str,
  encodeId2Base62Str,
  decodeBase62Str2Id,
  isValidUrl,
  failureResp,
  successResp,
} from '../libs/utils';
import { hasCache } from '../middlewares/cache';
import * as shortUrlService from '../services/short-url';
import {
  BaseResponse,
  CreateUrlReq,
  CreateUrlRes,
  GetUrlReq,
  GetUrlRes,
} from '../types';

const router = express.Router();
const lruCache = new LRU<string, string>({ max: 1000 }); // use default config

/**
 * reading the original url from a given short url
 * @param String {shortUrl}  the encoded short url
 * @returns {BaseResponse<GetUrlRes>} return the target long url
 */
router.get(
  '/:shortUrl',
  hasCache,
  async (req: Request<GetUrlReq>, res: Response<BaseResponse<GetUrlRes>>) => {
    const shortUrl = req.params.shortUrl;
    if (!isValidBase62Str(shortUrl)) {
      return res.json(failureResp('server received an invalid short url.'));
    }
    const id = decodeBase62Str2Id(shortUrl);
    const url = await shortUrlService.getUrl(id);
    const longUrl = url ? url.toJSON().longUrl : undefined;
    // set redis cache for reading
    if (longUrl) {
      await shortUrlService.saveToRedis(shortUrl, longUrl);
    }

    res.json(
      successResp<GetUrlRes>({
        longUrl,
      })
    );
  }
);

/**
 * creating a new short url for the given long url
 * @body {longUrl} the original long url
 * @returns {success: true, data: {shortUrl}} or {success: false, errMsg}
 */
router.post(
  '/addUrl',
  async (
    req: Request<{}, BaseResponse<CreateUrlRes>, CreateUrlReq>,
    res: Response<BaseResponse<CreateUrlRes>>
  ) => {
    const longUrl = req.body.longUrl;
    if (!isValidUrl(longUrl)) {
      return res.json(failureResp('server received an invalid long url.'));
    }
    /**
     * LRU cache: frequently submitted long urls will get the same id, sort of saving storage.
     * This is not intended to avoid all duplications acorss all containers, since it's in-memory cache.
     */
    const cacheShortUrl = lruCache.get(longUrl);
    if (cacheShortUrl) {
      console.log('lru cache hit');
      return res.json(successResp<CreateUrlRes>({ shortUrl: cacheShortUrl }));
    }
    const record = await shortUrlService.saveUrl(longUrl);
    if (!record) {
      return res.json(failureResp('server failed to save this long url.'));
    }
    const shortUrl = encodeId2Base62Str(record.toJSON().id);
    // lru cache
    lruCache.set(longUrl, shortUrl);

    // set redis cache for reading
    await shortUrlService.saveToRedis(shortUrl, longUrl);

    res.send(successResp<CreateUrlRes>({ shortUrl }));
  }
);

export { router };
