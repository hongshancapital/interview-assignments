import { describe, test, expect, beforeAll, } from 'vitest';
import request from 'supertest';
import app from '../src/app.js';
import Util from '../src/util.js';
import originUrlModel from '../src/originUrlModel.js';

describe('app intergration test', () => {
    const longUrl = 'https://www.tianyancha.com/';
    const shortUrl = 'https://s.com/1';
    const shortUrl2 = 'https://s.com/zzzq';
    const shortUrl3 = 'https://s.com/zzzqabaaba'
    const shortUrlPath = '1';
    beforeAll(async () => {
        const keyLong = originUrlModel.getShortToLongKey(shortUrlPath);
        const keyShort = originUrlModel.getLongToShortKey(longUrl);
        await originUrlModel.clearCache(keyLong);
        await originUrlModel.clearCache(keyShort);
        await originUrlModel.clearAllData();
    })
    describe('/longToShort', () => {
        test('/longToShort valid', async () => {
            const res = await request(app).post('/longToShort').send({ url: longUrl });
            expect(res.status).toEqual(Util.HTTP_CODE.OK);
            expect(res.body.result).toEqual(shortUrl);
        });

        test('/longToShort no url', async () => {
            const res = await request(app).post('/longToShort').send({});
            expect(res.status).toEqual(Util.HTTP_CODE.INVALID_PARAM);
        });
    });

    describe('/shortToLong', () => {
        test('/shortToLong  valid', async () => {
            const res = await request(app).post('/shortToLong').send({ url: shortUrl });
            expect(res.status).toEqual(Util.HTTP_CODE.OK);
            expect(res.body.result).toEqual(longUrl);
        });

        test('/shortToLong  no data', async () => {
            const res = await request(app).post('/shortToLong').send({ url: shortUrl2 });
            expect(res.status).toEqual(Util.HTTP_CODE.NOT_FOUND);
        });

        test('/shortToLong  no url', async () => {
            const res = await request(app).post('/shortToLong').send({});
            expect(res.status).toEqual(Util.HTTP_CODE.INVALID_PARAM);
        });

        test('/shortToLong  invalid url', async () => {
            const res = await request(app).post('/shortToLong').send({ url: shortUrl3 });
            expect(res.status).toEqual(Util.HTTP_CODE.NOT_FOUND);
        });
    });
});
