import { ShortUrlEntity } from '@entities/short-url.entity';
import { encode } from '@utils/base62';
import { redis } from '@utils/redis';

class ShortUrlService {
    public async createShortUrl(longUrl: string, expiredAt?: number) {
        const shortUrl = await this.genSortUrl();
        const res = await ShortUrlEntity.save({ shortUrl, longUrl, expiredAt: new Date(expiredAt) });
        return res;
    }
    public async findByShortUrl(shortUrl: string) {
        const longUrl = await redis.get(shortUrl);
        if (longUrl) {
            return ShortUrlEntity.create({ shortUrl, longUrl });
        }

        return ShortUrlEntity.findOneBy({ shortUrl });
    }

    public findByLongUrl(longUrl: string) {
        return ShortUrlEntity.findOneBy({ longUrl });
    }

    private async genSortUrl() {
        const delta = 1000;
        const id = await redis.incr('short-url:counter');
        const randomId = id * delta + Math.floor(Math.random() * delta);
        const shortUrl = encode(randomId);

        return shortUrl;
    }
}

export default ShortUrlService;
