import request from "supertest";

import app from "../../src/app";
import db from '../../src/db/db';

const req = request(app);

describe('shortUrl', () => {
  beforeAll(async () => {
    await db.raw(`use test_demo_database;`);
    // Seed anything
    await db('short_url')
      .insert(
        {
          id: 2,
          app_id: 'testId3',
          short_code: 'fef61c6c', // 伪造一个同参数一样的短码
          origin_url: 'http://www.baidu.com/2'
        }
      );
  });

  // afterAll(() => {
  //   本单元测试结束
  // });

  describe('生成短链', () => {
    it('生成短链-参数不全', async() => {
      const res = await req.post(`/shortUrl/generate`)
        .set('Content-type', 'application/json')
        .send({
            appId: 'sss'
        });
      expect(res.status).toBe(400);
      expect(JSON.parse(res.text).message).toStrictEqual('参数错误');
    });

    it('生成短链-长链接超长', async () => {
      const res = await req
        .post(`/shortUrl/generate`)
        .send({
          originUrl:
            'dfsdfdsfsdfdsfdsfdsfdsfdfsdfdsfsdfdsfdsfdsfdsfdddddfsdfdsfsdfdsfdsfdsfdsfdfsdfdsfsdfdsfdsfdsfdsfdfsdfdsfsdfdsfdsfdsfdsfdfsdfdsfsdfdsfdsfdsfdsfdfsdfdsfsdfdsfdsfdsfdsfdfsdfdsfsdfdsfdsfdsfdsfdfsdfdsfsdfdsfdsfdsfdsfdfsdfdsfsdfdsfdsfdsfdsf',
          appId: 'appId3'
        });
      expect(res.status).toBe(400);
      expect(JSON.parse(res.text).message).toStrictEqual('参数错误');
    });

    it('生成短链-短码被占用-相同originUrl', async () => {
      const [dup] = await db.select('*').from('short_url').where('id', 1);
      const res = await req
        .post(`/shortUrl/generate`)
        .send({
          originUrl: dup.origin_url,
          appId: dup.app_id
        });
      expect(res.status).toBe(200);
      expect(JSON.parse(res.text).shortUrl).toStrictEqual(
        `http://localhost:3000/short/${dup.short_code}`
      );
    });

    it('生成短链-短链被占用的情况-不同originUrl', async () => {
      const [exsit] = await db.select('*').from('short_url').where('id', 2);
      const res = await request(app)
        .post(`/shortUrl/generate`)
        .send({
          originUrl: 'http://www.test?a=b&c=d',
          appId: exsit.app_id
        });
      expect(res.status).toBe(200);
      expect(JSON.parse(res.text).shortUrl).not.toEqual(exsit.origin_url);
    });
  });

  describe('根据短链获取长链', () => {
    it('参数错误的情况下', async () => {
      const res = await req
        .get(`/shortUrl/getOriginUrl?short=noExist`);
      expect(res.status).toBe(400);
      expect(JSON.parse(res.text).message).toStrictEqual('参数错误');
    });
    it('参数超长的情况下', async () => {
      const res = await req
        .get(`/shortUrl/getOriginUrl?shortCode=ndsdfsdfdsfsdfoExist`);
      expect(res.status).toBe(400);
      expect(JSON.parse(res.text).message).toStrictEqual('参数错误');
    });

    it('短码存在的情况下', async () => {
      const [seed] = await db.select('*').from('short_url').where('id', 1);
      const res = await req
        .get(`/shortUrl/getOriginUrl?shortCode=${seed.short_code}`);
      expect(res.status).toBe(200);
      expect(JSON.parse(res.text).originUrl).toEqual(seed.origin_url);
    });

    it('短链不存在', async () => {
      const res = await req
        .get(`/shortUrl/getOriginUrl?shortCode=noExist`);
      expect(res.status).toBe(400);
      expect(JSON.parse(res.text).message).toStrictEqual('短链不存在');
    });
  });
});
