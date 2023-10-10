import { describe, test, expect, beforeEach, } from 'vitest';
import urlService from '../src/urlService.js';
import originUrlModel from '../src/originUrlModel.js';

describe('urlService unit test', () => {
    const longUrl = 'https://www.tianyancha.com/';
    const shortUrl = 'https://s.com/1';
    const shortPath = '1';

    beforeEach(async () => {
        const keyLong = originUrlModel.getShortToLongKey(shortPath);
        const keyShort = originUrlModel.getLongToShortKey(longUrl);
        await originUrlModel.clearCache(keyLong);
        await originUrlModel.clearCache(keyShort);
        await originUrlModel.clearAllData();
    });

    describe('longToShort', () => {
        test('normal', async () => {
            // 1. no data, insert
            let shortResult = await urlService.longToShort(longUrl);
            expect(shortResult).toEqual(shortUrl);

            // 2. read from cache
            shortResult = await urlService.longToShort(longUrl);
            expect(shortResult).toEqual(shortUrl);

            // 3. clear cache, read from db
            const cacheKey = originUrlModel.getLongToShortKey(longUrl);
            await originUrlModel.clearCache(cacheKey);
            shortResult = await urlService.longToShort(longUrl);
            expect(shortResult).toEqual(shortUrl);
        });
    })

    describe('shortToLong', () => {
        test('normal', async () => {
            // 1. no data
            let longUrlResult = await urlService.shortToLong(shortPath);
            expect(longUrlResult).toBe(null);

            // 2. add data, access from db
            await originUrlModel.addUrl(longUrl);
            longUrlResult = await urlService.shortToLong(shortPath);
            expect(longUrlResult).toEqual(longUrl);

            // 3. read from cache
            longUrlResult = await urlService.shortToLong(shortPath);
            expect(longUrlResult).toEqual(longUrl);
        });

    })
});