/**
 * app 集成测试
 */
require("dotenv").config();
import supertest from 'supertest';
import { app } from '../src/app';

describe('PUT /add', () => {
    it('500 no url', async () => {
        const res = await supertest(app).put('/add').send({});
        expect(res.status).toBe(500);
    })

    it('500 invalid url', async () => {
        const res = await supertest(app).put('/add').send({
            "url": "abc"
        });
        expect(res.status).toBe(500);
    })

    it('normal request', async () => {
        const res = await supertest(app).put('/add').send({
            "url": "http://www.baidu.com"
        });
        expect(res.status).toBe(200);
    })
})

describe('GET /:shortId', () => {
    it('invalid shortId', async () => {
        const res = await supertest(app).get('/aaaa');
        expect(res.status).toBe(500);
    })

    it('404 shortId', async () => {
        const res = await supertest(app).get('/aaaaaaaa');
        expect(res.status).toBe(404);
    })

    it('query after add', async () => {
        const res = await supertest(app).put('/add').send({
            "url": "http://www.baidu.com"
        });
        const result = JSON.parse(res.text);
        expect(result.unionId).not.toBeNull();
        const res2 = await supertest(app).get(`/${result.unionId}`)
        expect(res2.status).toBe(301);
    })
})
