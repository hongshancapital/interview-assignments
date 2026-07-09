import { ShortUrlEntity } from '@entities/short-url.entity';
import { HttpException } from '@exceptions/HttpException';
import { getRedisKey, redis } from '@utils/redis';
import Base62 from '@utils/base62';

class ShortUrlService {
    private readonly redisKeyPrefix = 'short-url';
    private readonly base62 = new Base62();
    public async createShortUrl(longUrl: string, expiredAt?: number) {
        if (expiredAt && expiredAt < Date.now()) {
            throw new HttpException(400, 'Invalid expireAt time');
        }

        const shortUrl = await this.genSortUrl();
        const shortUrlEntity = await ShortUrlEntity.save({
            shortUrl,
            longUrl,
            expiredAt: expiredAt && new Date(expiredAt)
        });

        this.cacheUrl(shortUrlEntity);

        return shortUrlEntity;
    }
    public async findByShortUrl(shortUrl: string) {
        const longUrl = await redis.get(shortUrl);
        if (longUrl) {
            return ShortUrlEntity.create({ shortUrl, longUrl });
        }

        const shortUrlEntity = await ShortUrlEntity.findOneBy({ shortUrl });
        if (!shortUrlEntity) {
            throw new HttpException(404, 'url not found');
        }
        if (shortUrlEntity?.expiredAt && shortUrlEntity.expiredAt.getTime() < Date.now()) {
            throw new HttpException(404, 'short url is expired');
        }

        this.cacheUrl(shortUrlEntity);

        return shortUrlEntity;
    }

    private async genSortUrl() {
        const id = await redis.incr('short-url:counter');
        const finalId = id * 62 ** 2 + Math.floor(Math.random() * 62 ** 2);
        const shortUrl = this.base62.encode(finalId);

        return shortUrl;
    }

    private async cacheUrl(shortUrlEntity: ShortUrlEntity) {
        const cacheKey = getRedisKey(this.redisKeyPrefix, shortUrlEntity.shortUrl);
        let cacheTTL = 3600 * 24 * 1000;
        if (shortUrlEntity.expiredAt) {
            cacheTTL = shortUrlEntity.expiredAt.getTime() - Date.now();
        }
        await redis.set(cacheKey, shortUrlEntity.longUrl, 'PX', cacheTTL);
    }
}

export default ShortUrlService;
