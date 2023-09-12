import { Request, Response, NextFunction } from "express";

import { ApplicationError } from "../helpers/application.err";
import { isCode, isUrl } from "../utils/validator";

import { RedisService } from "../services/redis.service";
import { UrlService } from "../services/url.service";
import { StatisticsService } from "../services/statistics.service";
import { urlDecode, urlEncode } from "../utils/transfer";
import { Statistics } from "../interfaces/statistics.interface";
import { Url } from "../interfaces/url.interface";
import { logger } from '../utils/logger';
import { container, singleton } from 'tsyringe';

@singleton()
export class UrlController {
  private redisPrefix = 'url';
  private bloomFilter = {
    name: 'bloom:code',
    rate: 0.000000001,
    capacity: 10000000000,
    options: {
      EXPANSION: 20000000000,
    }
  };

  constructor(
    protected redisService: RedisService,
    protected urlService: UrlService,
    protected statisticsService: StatisticsService
  ) {
    this.init().then().catch(error=> {
      logger.error('UrlController error:',error);
    });
  }

  async init() {
    await this.redisService.createBloom(this.bloomFilter.name, this.bloomFilter.rate, this.bloomFilter.capacity);
  }

  private getCacheUrlKey(key: string): string {
    return `${this.redisPrefix}:origin:${key}`;
  }

  private getCacheCodeKey(key: string): string {
    return `${this.redisPrefix}:code:${key}`;
  }

  private getShortUrl(code: string): string {
    return `http://127.0.0.1:3000/${code}`;
  }

  private getStatistic(req: Request, urlUid: string): Statistics{ 
    const statistic = {
      urlUid: urlUid,
      ip: req.ip,
      referer: '',
      userAgent:  '',
      language: '',
      accept:  ''
    } as Statistics;
    try {
      statistic.referer = req.header('referer') || '';
      statistic.userAgent = req.header('User-Agent') || '';
      statistic.language = req.header('Accept-language') || '';
      statistic.accept = req.header('Accept') || '';
    } catch (error) {
      logger.error('Url controller getStatistic error', error);
    }
    return statistic;
  }

  /**
  * @api {post} /url Create a short Url
  * @apiName Create
  * @apiPermission user
  * @apiGroup Url
  *
  * @apiBody {String} url the origin url
  *
  * @apiSuccess {String} url  The short Url
  * @apiSuccessExample {json} 200
  *     HTTP/1.1 200 OK
  *     {
  *         "url": "http://127.0.0.1:3000/sdaasd"
  *     }
  * 
  * @apiErrorExample 400:
  *     HTTP/1.1 400 Invalid URL
  *     {
  *       "message": "Invalid URL"
  *     }
  */
  public async create(req: Request, res: Response, next: NextFunction) {
    try {
      const url: string = req.body.url;
      if (!url || !isUrl(url)) {
        throw new ApplicationError(400, "Invalid URL");
      }
      const userUid = (req as any).user?.uid;
      
      const safeUrl = urlEncode(url);
      const urlRedisKey = this.getCacheUrlKey(`${userUid}:${safeUrl}`);
      const cacheCode: string | null = await this.redisService.get(urlRedisKey);
      if(cacheCode) {
        res.json({ url: this.getShortUrl(cacheCode) });
        return;
      }

      const existed = await this.urlService.findByOption(safeUrl, userUid);
      if (existed) {
        await this.redisService.setEx(urlRedisKey, existed.code);
        res.json({ url: this.getShortUrl(existed.code) });
        return;
      }

      const create = await this.urlService.createByOption(safeUrl, userUid);
      await this.redisService.setEx(urlRedisKey, create.code);
      await this.redisService.bloomAdd(this.bloomFilter.name, create.code);
      await this.redisService.setEx(this.getCacheCodeKey(create.code), JSON.stringify( {
        uid: create.uid,
        code: create.code,
        url: create.url
      }));

      res.json({ url: this.getShortUrl(create.code) });
    } catch (error) {
      next(error);
    }
  }

  /**
  * @api {get} /:code Request code information
  * @apiName Redirect
  * @apiGroup Url
  *
  * @apiParam {String} code the short url code
  *
  * @apiSuccessExample 302
  *     HTTP/1.1 302
  *     "http://127.0.0.1:3000/sdaasd"
  * 
  * @apiErrorExample 400:
  *     HTTP/1.1 400 Invalid Code
  *     {
  *       "message": "Invalid Code"
  *     }
  * @apiErrorExample 404:
  *     HTTP/1.1 404 URL not existed
  *     {
  *       "message": "URL not existed"
  *     }
  */
  public async redirect(req: Request, res: Response, next: NextFunction) {
    try {
      const code = req.params.code;
      if (!code || !isCode(code)) {
        throw new ApplicationError(400, "Invalid Code");
      }

      const exists = await this.redisService.bloomExists(this.bloomFilter.name, code);
      if (!exists) {
        throw new ApplicationError(404, "URL not existed");
      }

      const codeRedisKey = this.getCacheCodeKey(code);
      const cacheUrl: string | null = await this.redisService.get(codeRedisKey);
      if (cacheUrl) {
        const originUrl: Url = JSON.parse(cacheUrl);
        
        const statistic = this.getStatistic(req, originUrl.uid);
        setImmediate(async ()=> {
          await this.statisticsService.createByOption(statistic);
        });

        res.redirect(302, originUrl.url);
        return;
      }

      const dbUrl = await this.urlService.findByCode(code);
      if (!dbUrl) {
        throw new ApplicationError(404, "URL not existed");
      }

      const decodeUrl = urlDecode(dbUrl.url);
      await this.redisService.setEx(codeRedisKey, JSON.stringify( {
        uid: dbUrl.uid,
        code: dbUrl.code,
        url: decodeUrl
      }));
      const statistic = this.getStatistic(req, dbUrl.uid);
      setImmediate(async ()=> {
        await this.statisticsService.createByOption(statistic);
      });
      res.redirect(302, decodeUrl);
    } catch (error) {
      next(error);
    }
  }

}

const urlController = container.resolve(UrlController);
export default urlController;
