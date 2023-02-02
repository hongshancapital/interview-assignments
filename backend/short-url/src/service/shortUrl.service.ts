import { ShortUrlProvider } from '../provider/shortUrl.provider'
import { createShortUrl } from '../common/utils/shortUrlCreate.utils'
import { redis } from '../common/cache/redis.cache';
import { RedisClusterType,RedisClientType } from 'redis';
import { FilterType } from '../common/interface/bloomFilter.interface';

class ShortUrlService {
  private shortUrlProvider: ShortUrlProvider;
  private redis: RedisClientType | RedisClusterType;

  constructor(shortUrlProvider:ShortUrlProvider){
    this.shortUrlProvider = shortUrlProvider;
    this.redis = redis;
  };
  
  public async saveShortUrl(longUrl: string) {
    const isExist = await this.filterUrl(longUrl,FilterType.LongUrl);

    if(isExist) {
      const shortUrl = await this.shortUrlProvider.getShortUrlByLongUrl(longUrl);
      if(shortUrl) {
        return shortUrl;
      }
    }

    const shortUrl = await createShortUrl();
    const shortUrlInDb = await this.shortUrlProvider.saveShortUrl(shortUrl,longUrl);
    return shortUrlInDb;
  }

  public async getShortUrlByLongUrl(longUrl: string) {
    const shortUrl = await this.shortUrlProvider.getShortUrlByLongUrl(longUrl);
    if(!shortUrl) {
      throw new Error('获取短链接失败');
    }
    return shortUrl;
  }

  public async getLongUrlByShortUrl(shortUrl: string) {
    const isExist = await this.filterUrl(shortUrl,FilterType.ShortUrl);
    if(!isExist) {
      throw new Error('长链接不存在');
    }
    const longUrl = await this.shortUrlProvider.getLongUrlByShortUrl(shortUrl);
    if(!longUrl) {
      throw new Error('获取长链接失败');
    }
    return longUrl;
  }
  
  public async filterUrl(key: string, type: FilterType) {
    return this.redis.bf.EXISTS(type,key);
  }

}
export { ShortUrlService };