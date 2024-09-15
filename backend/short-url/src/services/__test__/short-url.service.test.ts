import { closeDB, loadDB } from '@utils/database';
import { closeRedis } from '@utils/redis';
import ShortUrlService from '@services/short-url.service';

const mockLongUrl = 'https://www.google.com';

describe('Short URL Service', () => {
    beforeAll(async () => {
        await loadDB();
    });

    afterAll(async () => {
        await closeDB();
        await closeRedis();
    });

    it('Should create a shortUrl', async () => {
        const shortUrlService = new ShortUrlService();

        const randomUrl = `${mockLongUrl}?r=${Math.floor(Math.random() * 10000)}`;
        const createDto = { longUrl: randomUrl, expiredAt: Date.now() + 1000 * 60 };
        const shortUrlEntity = await shortUrlService.createShortUrl(createDto.longUrl, createDto.expiredAt);

        expect(shortUrlEntity.longUrl).toEqual(randomUrl);
    });

    it('Should find the object with short url', async () => {
        const shortUrlService = new ShortUrlService();

        const randomUrl = `${mockLongUrl}?r=${Math.floor(Math.random() * 10000)}`;
        const createDto = { longUrl: randomUrl, expiredAt: Date.now() + 1000 * 60 };
        const shortUrlEntity = await shortUrlService.createShortUrl(createDto.longUrl, createDto.expiredAt);
        expect(shortUrlEntity?.shortUrl).toBeDefined();

        const entity = await shortUrlService.findByShortUrl(shortUrlEntity.shortUrl);
        expect(entity?.longUrl).toEqual(randomUrl);
    });
});
