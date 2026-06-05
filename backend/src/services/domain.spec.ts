import DomainService from './domain';
import Mongo from '@/db/mongodb';

describe('domain service', () => {
    beforeAll(async () => {
        await Mongo.connect();
    });

    afterAll(async () => {
        await Mongo.server.dropDatabase();
        await Mongo.close();
    });

    let testDomain: null | { compressed: string } = null;
    const testUrl = 'https://www.expressjs.com.cn/starter/hello-world.html';
    const testName = 'express';

    it('should throw invalid long url', async () => {
        await expect(DomainService.addDomain('invalid-url')).rejects.toThrow();
    });

    it('should insert success', async () => {
        const result = await DomainService.addDomain(testUrl, testName);

        expect(result).toHaveProperty('compressed');
        testDomain = { compressed: result.compressed };
    });

    it('should throw already exist', async () => {
        await expect(DomainService.addDomain(testUrl, testName)).rejects.toThrow();
    });

    it('should throw invalid short url', async () => {
        await expect(DomainService.getDomainByShortUrl('invalid-short-url')).rejects.toThrow();
    });

    it('should throw not found', async () => {
        await expect(DomainService.getDomainByShortUrl(`https://www.expressjs.com.cn/${Math.random().toString(36).substring(2, 8)}`)).rejects.toThrow();
    });

    it('should query success', async () => {
        expect(await DomainService.getDomainByShortUrl(testDomain?.compressed)).toHaveProperty('compressed');
    });
});
