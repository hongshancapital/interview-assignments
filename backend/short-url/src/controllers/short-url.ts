import { ShortUrl } from 'src/entities/short-url';
import * as ShortUrlService from 'src/services/short-url';

async function create(longUrl: string, expiredAt?: number): Promise<ShortUrl> {
    const doc = await ShortUrlService.findByLongUrl(longUrl);

    if (doc) {
        return doc;
    }
    return ShortUrlService.createShortUrl(longUrl, expiredAt);
}

async function findByShortUrl(shortUrl: string): Promise<ShortUrl> {
    return ShortUrlService.findByShortUrl(shortUrl);
}

export default {
    create,
    findByShortUrl
};
