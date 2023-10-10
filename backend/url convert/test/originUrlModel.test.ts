import { describe, test, expect, beforeEach, } from 'vitest';
import originUrlModel from '../src/originUrlModel.js';

describe('originUrlModel unit test', () => {
    const longUrl = 'https://www.tianyancha.com/';
    const shortUrlPath = '1';

    beforeEach(async () => {
        const keyLong = originUrlModel.getShortToLongKey(shortUrlPath);
        const keyShort = originUrlModel.getLongToShortKey(longUrl);
        await originUrlModel.clearCache(keyLong);
        await originUrlModel.clearCache(keyShort);
        await originUrlModel.clearAllData();
    });

    describe('cacheLongToShort', () => {
        test('normal', async () => {
            await originUrlModel.cacheLongToShort(longUrl, shortUrlPath);
            const cachedResult = await originUrlModel.getShortFromCache(longUrl);
            expect(cachedResult).toEqual(shortUrlPath);
        });
    });

    describe('cacheShortToLong', () => {
        test('normal', async () => {
            await originUrlModel.cacheShortToLong(shortUrlPath, longUrl);
            const cachedResult = await originUrlModel.getLongFromCache(shortUrlPath);
            expect(cachedResult).toEqual(longUrl);
        });
    });

    describe('getIdByLongUrl', () => {
        test('normal', async () => {
            // no data
            let resultId = await originUrlModel.getIdByLongUrl(longUrl);
            expect(resultId).toBe(null);

            // add data,
            await originUrlModel.addUrl(longUrl);
            resultId = await originUrlModel.getIdByLongUrl(longUrl);
            expect(resultId).toEqual(1);
        });
    });

    describe('getLongUrlById', () => {
        test('normal', async () => {
            const searchId = 1;
            let resultUrl = await originUrlModel.getLongUrlById(searchId);
            expect(resultUrl).toBe(null);

            await originUrlModel.addUrl(longUrl);
            resultUrl = await originUrlModel.getLongUrlById(searchId);
            expect(resultUrl).toEqual(longUrl);
        });
    });

    describe('addUrl', () => {
        test('normal', async () => {
            let resultId = await originUrlModel.getIdByLongUrl(longUrl);
            expect(resultId).toBe(null);

            resultId = await originUrlModel.addUrl(longUrl);
            expect(resultId).toEqual(1);
        });
    });
});
