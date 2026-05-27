/**
 * @file 短连接缓存
 * @author zengbaoqing<misterapptracy@gmail.com>
 */
'use strict';
import { Service } from 'egg';

export default class ShortUrlCacheService extends Service {

  private readonly cacheKey = 'su:{shortUrl}';

  private readonly maxAge = 30 * 24 * 3600;

  private getCacheKey(shortUrl: string) {
    return this.cacheKey.replace('{shortUrl}', shortUrl);
  }

  public async refresh(shortUrl: string) {
    const { app } = this;
    return !!(await app.redis.expire(this.getCacheKey(shortUrl), this.maxAge));
  }

  public async getData(shortUrl: string) {
    const { app } = this;
    return await app.redis.get(this.getCacheKey(shortUrl));
  }

  public async setData(shortUrl: string, longUrl: string) {
    const { app } = this;
    await app.redis.set(this.getCacheKey(shortUrl), longUrl, 'EX', this.maxAge);
  }

}
