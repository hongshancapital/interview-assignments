import { expect } from 'chai';
import supertest from 'supertest';
import app from '../src/app';

describe('短域名测试', function () {

    let shortCode: string;

    it('生成短链', async function () {
        const res = await supertest(app.callback())
            .post('/api/shortCode')
            .send({
                url: 'https://www.baidu.com/s?wd=node',
                query: {
                    test: 1,
                },
            })
            .expect(200);
        expect(res.body.code).to.be.equal(0);
        expect(res.body.data.shortCode).to.be.a('string');

        shortCode = res.body.data.shortCode;
    });

    it('获取短链信息', async function () {
        const res = await supertest(app.callback())
            .get('/api/shortCode')
            .query({
                shortCode,
            })
            .expect(200);
        expect(res.body.code).to.be.equal(0);
        expect(res.body.data.url).to.be.a('string');
    });

    it('获取短链信息-短链不存在', async function () {
        await supertest(app.callback())
            .get('/api/shortCode')
            .query({
                shortCode: 'test',
            })
            .expect(404);
    });

    it('短链重定向', async function () {
        await supertest(app.callback())
            .get(`/${shortCode}`)
            .expect(302);
    });

    it('短链重定向-短链不存在', async function () {
        await supertest(app.callback())
            .get('/test')
            .expect(404);
    });
});
