import { describe, expect, it } from '@jest/globals';
import supertest from 'supertest';
import Application from '../src/app';

describe('shortUrlService', () => {
    let app: Application;
    let request: supertest.SuperTest<supertest.Test>
    beforeEach(async () => {
        app = new Application();
        await app.init();
        request = supertest(app.callback());
    });

    describe('root', () => {
        it('get notfound', async () => {
            const result = await request.get('/find?short_url=0');
            expect(result.statusCode).toBe(404);
            expect(result.body.meta.message).toEqual('not found');
        });

        it('insert and get', async () => {
            const longUrl = `https://baidu.com/t${Date.now()}`;

            const insertRs = await request.post('/insert').send({
                url: longUrl
            });
            const shortUrl = insertRs.body.data.short_url;

            expect(insertRs.statusCode).toBe(200);
            expect(typeof shortUrl).toEqual('string');

            const findRs = await request.get('/find?short_url=' + encodeURIComponent(shortUrl));
            expect(findRs.statusCode).toBe(200);
            expect(findRs.body.data.long_url).toEqual(longUrl);
        })
    });
});
