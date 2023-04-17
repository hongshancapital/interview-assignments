import "reflect-metadata";

import request from 'supertest';
import app from '../../../src/server';
import config from "../../../src/config";

import { RATE_LIMIT_MAX_REQUESTS, DAILY_TOTAL_LIMIT_MAX_REQUESTS, RATE_LIMIT_BLACKLIST_EXPIRE } from "../../../src/middlewares/black.middleware";
import { generateToken } from "../../../src/utils/jwt";
import teardown from "../teardown";
import setup from "../setup";


describe('Redirect to origin url, API: /:code', () => {
  const endpoint = '/';
  const url = 'www.baidu.com/redirect';
  const redisClient: any = app.redisService.getClient();
  let token: string;
  let code: string;

  beforeAll(async () => {
    await setup();
    token = await generateToken({  uid: 'test-uid', }, 10000, config.get('secretKey'));
    const { body } = await request(app.getServer())
      .post('/url')
      .set('Authorization', `Bearer ${token}`)
      .send({
        url: url
      }).expect(200);
    const arr: [] = body.url.split('/');
    code = arr[arr.length - 1];
  });

  afterAll(async ()=> {
    await teardown();
  });

  describe(`[GET] ${endpoint}:code`, () => {

    it('should successfully get origin url success from db', async () => {
      await request(app.getServer())
        .get(`${endpoint}${code}`)
        .expect(302);
    });

    it('should successfully get origin url from redis', async () => {
      await request(app.getServer())
        .get(`${endpoint}${code}`)
        .expect(302);
    });

    it('should failed the code does not existed', async () => {
      await request(app.getServer())
        .get(`${endpoint}1a1d`)
        .expect(404);
    });

    it('should failed the code is wrong', async () => {
      await request(app.getServer())
        .get(`${endpoint}2!1321`)
        .expect(400);
    });

    it('should failed the code length > 8', async () => {
      await request(app.getServer())
        .get(`${endpoint}1232111321`)
        .expect(400);
    });

  });

  describe(`[GET] ${endpoint}:code blacklist`, () => { 
    it('should forbidden because in blacklist', async () => {
      const promises: Promise<any>[] = [];
      for (let i = 0; i < RATE_LIMIT_MAX_REQUESTS+1; i++) {
        promises.push(request(app.getServer()).get(`${endpoint}${code}`).timeout(10000).then(response => response.body));
      }
      const results = await Promise.all(promises);
      const errorResult = results.find(body => body.status === 429);
      expect(errorResult).toBeUndefined();

      const result = await request(app.getServer()).get(`${endpoint}${code}`);
      expect(result.body.status).toBe(403);
      const key = '::ffff:127.0.0.1:undefined';
      const blackKey = app.redisService.getKey(`black:${key}`);
      await redisClient.del(blackKey);
    });
  });

  describe(`[GET] ${endpoint}:code too many request`, () => { 
    it('should forbidden too many request max daily total count', async () => {
      const key = '::ffff:127.0.0.1:undefined';
      const requestKey = app.redisService.getKey(`requests:total:${key}`);
      const blackKey = app.redisService.getKey(`black:${key}`);
      await redisClient.del(blackKey);
      await redisClient.del(requestKey);
      await redisClient.set(requestKey, DAILY_TOTAL_LIMIT_MAX_REQUESTS, {
        EX: RATE_LIMIT_BLACKLIST_EXPIRE,
        NX: true,
      });
      const result = await request(app.getServer()).get(`${endpoint}${code}`);
      expect(result.body.status).toBe(429);
      await redisClient.del(requestKey);
    });
  });
  
});
