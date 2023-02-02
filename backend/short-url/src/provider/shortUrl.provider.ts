import { DbConnection } from '../common/db/dbConnectionOrm.db';
import { ShortUrl } from '../entities/shortUrl.entity';
import { RedisClusterType,RedisClientType } from 'redis';
import { redis } from '../common/cache/redis.cache';
class ShortUrlProvider {
  private redis: RedisClientType | RedisClusterType;
  constructor(){
    this.redis = redis;
  };

  public async saveShortUrl(shortUrl: string,longUrl: string): Promise<string> {
    const dbConnection = await DbConnection.getInstance();

    const isExist = await this.getShortUrlByLongUrl(longUrl);
    if(isExist) {
      return isExist;
    }
    
    const url = await dbConnection.getRepository(ShortUrl).save(ShortUrl.ValueOf({
      shortUrl,
      longUrl,
    }));

    // 落库之后添加 bloom 
    await this.redis.bf.ADD('shortUrl',url.shortUrl);
    await this.redis.bf.ADD('longUrl',url.longUrl);

    await this.redis.SETEX(longUrl, 7200, url.shortUrl);
    return url.shortUrl;
  }

  public async getShortUrlByLongUrl(longUrl: string): Promise<string | null> {
    const isExistInCache = await this.redis.get(longUrl);
    if(isExistInCache) {
      return isExistInCache; 
    }

    const dbConnection = await DbConnection.getInstance();

    const url = await dbConnection.getRepository(ShortUrl).findOne({
      where:{
        longUrl,
      },
      select:['shortUrl'],
    });
    if(!url) {
      return null;
    }

    this.redis.setEx(longUrl, 7200, url.shortUrl);
    return url.shortUrl;
  }

  public async getLongUrlByShortUrl(shortUrl: string): Promise<string | null> {
    const isExistInCache = await this.redis.get(shortUrl);
    if(isExistInCache) {
      return isExistInCache; 
    }

    const dbConnection = await DbConnection.getInstance();
    
    const url = await dbConnection.getRepository(ShortUrl).findOne({
      where:{
        shortUrl,
      },
      select:['longUrl'],
    });
    if(!url) {
      return null;
    }
    this.redis.setEx(shortUrl, 7200, url.longUrl);
    return url.longUrl;
  }
}

export { ShortUrlProvider };