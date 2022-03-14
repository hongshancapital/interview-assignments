import { Provide, Inject, Config } from '@midwayjs/decorator';

import { LocalRedisService } from './redis.service';
import { IdToShortURL, ShortUrlToId } from '../util/convert';
import { sha256 } from '../util/hash';
import { UrlModel } from '../entity/url';

const ShortUrlDomainPrefix = 'https://withnate.cn/';

@Provide()
export class UrlService {
  @Inject()
  redisService: LocalRedisService;

  @Config('salt')
  salt;

  async getByShortUrl(url: string) {
    if (!url.startsWith(ShortUrlDomainPrefix)) {
      return {
        code: 10001,
        msg: 'params error',
      };
    }
    url = url.slice(ShortUrlDomainPrefix.length);
    if (!url || url.length < 2) {
      return {
        code: 10001,
        msg: 'params error',
      };
    }
    // 校验salt
    const saltChar = sha256(url.slice(0, url.length - 1), this.salt.sha);
    if (saltChar !== url.slice(url.length - 1)) {
      return {
        code: 10002,
        msg: 'url not valid',
      };
    }

    // redis bf
    try {
      const existRes = await this.redisService.bfExist(url);
      if (!existRes) {
        return {
          code: 20001,
          msg: 'url not exist',
          data: {},
        };
      }
    } catch (e) {
      console.log('redis_bf_error', e);
    }

    // 先请求redis 查询
    try {
      const longUrl: string = await this.redisService.get(url);
      if (longUrl) {
        console.log('读redis 缓存');
        return {
          code: 0,
          msg: 'ok',
          data: {
            url: longUrl,
          },
        };
      }
    } catch (e) {
      console.log('redis_read_error', e);
    }

    const id = ShortUrlToId(url);
    const urlMapRes = await UrlModel.findByPk(id);
    if (!urlMapRes) {
      return {
        code: 20001,
        msg: 'url not exist',
        data: {},
      };
    }

    return {
      code: 0,
      msg: 'ok',
      data: {
        url: urlMapRes.origin,
      },
    };
  }

  async createByLongUrl(url: string) {
    if (!url) {
      return {
        code: -1,
        msg: 'params error',
      };
    }
    // 校验url是否合规
    if (!url.startsWith('https') && !url.startsWith('http')) {
      return {
        code: '10001',
        msg: 'params error',
      };
    }
    // 先请求redis 查询
    try {
      const longUrl: string = await this.redisService.get(url);
      if (longUrl) {
        console.log('读redis 缓存');
        return {
          code: 0,
          msg: 'ok',
          data: {
            url: longUrl,
          },
        };
      }
    } catch (e) {
      console.log('redis_read_error', e);
    }
    // 不存在，则生成
    const createRes = await UrlModel.create({
      shorturl: '',
      origin: url,
    });
    if (!createRes.id) {
      return {
        code: -1,
        msg: 'system_err',
        data: {},
      };
    }
    let shortUrl = IdToShortURL(createRes.id);
    if (shortUrl.length > 7) {
      return {
        code: -1,
        msg: 'max system limit',
        data: {},
      };
    }
    const saltChar = sha256(shortUrl, this.salt.sha);
    shortUrl += saltChar;
    console.log(`shortUrl : ${shortUrl}`);
    // 先添加redis bf
    await this.redisService.bfAdd(shortUrl);

    await createRes.update({
      shorturl: shortUrl,
    });

    const finalShortUrl = ShortUrlDomainPrefix + shortUrl;
    await this.redisService.set(url, finalShortUrl);
    await this.redisService.set(shortUrl, url);

    return {
      code: 0,
      msg: 'ok',
      data: {
        url: finalShortUrl,
      },
    };
  }
}
