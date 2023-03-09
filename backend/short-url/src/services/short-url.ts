import { ShortUrl } from 'src/entities/short-url';
import { dataSource, encode } from 'src/utils';
import { redis } from 'src/utils/redis';

export async function createShortUrl(longUrl: string, expiredAt?: number) {
    const shortUrl = await getSortUrl();
    const res = await dataSource.getRepository(ShortUrl).save({ shortUrl, longUrl, expiredAt: new Date(expiredAt) });
    return res;
}

export function findByLongUrl(longUrl: string) {
    return dataSource.getRepository(ShortUrl).findOneBy({ longUrl });
}

export async function findByShortUrl(shortUrl: string) {
    const longUrl = await redis.get(shortUrl);
    if (longUrl) {
        return ShortUrl.create({ shortUrl, longUrl });
    }

    return dataSource.getRepository(ShortUrl).findOneBy({ shortUrl });
}

async function getSortUrl() {
    const delta = 1000;
    const id = await redis.incr('short-url:counter');
    const randomId = id * delta + Math.floor(Math.random() * delta);
    const shortUrl = encode(randomId);

    return shortUrl;
}
