import { describe, expect, it } from '@jest/globals';
import { ShortUrlService } from "../../../src/service/short_url";
import Application from "../../../src/app";
import { baseConfig } from "../../../src/config";
import { ResourceNotFound } from "../../../src/middleware/error";

describe('shortUrlService', () => {
    let shortUrlService: ShortUrlService;

    beforeEach(async () => {
        const app = new Application();
        await app.init();
        const ctx = app.createAnonymousContext();
        shortUrlService = new ShortUrlService(ctx);
    });

    describe('method', () => {
        it('should string10to62', () => {
            const count = 10006;
            expect(shortUrlService.string10to62(count)).toEqual('2Bo');
        });

        it('should insert and get', async () => {
            const longUrl = `https://baidu.com/t${Date.now()}`;

            // insert
            const shortUrl = await shortUrlService.insertUrl(longUrl);
            expect(typeof shortUrl).toEqual('string');

            // insert duplicate
            const shortUrl2 = await shortUrlService.insertUrl(longUrl);
            expect(shortUrl2).toEqual(shortUrl);

            // find
            const findLongUrl = await shortUrlService.find(shortUrl);
            expect(findLongUrl).toBe(longUrl);

            // find not found
            await expect(shortUrlService.find('0')).rejects.toThrow(ResourceNotFound);
        });
    });
});
