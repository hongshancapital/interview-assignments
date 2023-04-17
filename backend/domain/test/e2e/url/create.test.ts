import "reflect-metadata";

import request from 'supertest';
import app from '../../../src/server';
import config from '../../../src/config';
import { generateToken } from '../../../src/utils/jwt';
import teardown from "../teardown";
import setup from "../setup";
import { RATE_LIMIT_MAX_REQUESTS } from "../../../src/middlewares/black.middleware";

describe('API: /url', () => {
  const endpoint = '/url';
  let token: string;
  let token2: string;

  beforeAll(async () => {
    await setup();
    token = await generateToken({ uid: 'test1' }, 10000, config.get('secretKey'));
    token2 = await generateToken({ uid: 'test2' }, 10000, config.get('secretKey'));
  });

  afterAll(async ()=> {
    await teardown();
  });

  describe(`[POST] ${endpoint}`, () => {

    it('should failed authentication required', async () => {
      await request(app.getServer())
        .post(endpoint)
        .expect(404);
    });

    it('should failed use wrong authentication', async () => {
      await request(app.getServer())
        .post(endpoint)
        .set('Authorization', `Bearer wrong-token`)
        .expect(401);
    });

    it("'should failed use wrong cookies", async () => {
      const url = 'www.baidu.com/redirect';
      await request(app.getServer())
        .post('/url')
        .set('Cookie',  [`Authorization=wrong-token`])
        .send({
          url: url
        }).expect(401);
    });

    it('should failed create short url use wrong url', async () => {
      const wrongUrl = 'baidu.com/<script>alert("hello")</script>';
      await request(app.getServer())
        .post(endpoint)
        .set('Authorization', `Bearer ${token}`)
        .send({
          url: wrongUrl
        }).expect(400);
    });

    it("'should successfully create short url with cookies", async () => {
      const url = 'baidu.com/cookies';
      const { body } = await request(app.getServer())
        .post('/url')
        .set('Cookie',  [`Authorization=${token}`])
        .send({
          url: url
        }).expect(200);
      
      expect(body).toHaveProperty('url');
    });

    it('should successfully create short url', async () => {
      const url = 'baidu.com/authorization';
      const { body } = await request(app.getServer())
        .post(endpoint)
        .set('Authorization', `Bearer ${token}`)
        .send({
          url: url
        }).expect(200);
      expect(body).toHaveProperty('url');
    });

    it('should successfully create short url use same url', async () => {
      const url = 'baidu.com/authorization2';
      const { body } = await request(app.getServer())
        .post(endpoint)
        .set('Authorization', `Bearer ${token}`)
        .send({
          url: url
        }).expect(200);
      expect(body).toHaveProperty('url');

      const res2 = await request(app.getServer())
        .post(endpoint)
        .set('Authorization', `Bearer ${token}`)
        .send({
          url: url
        }).expect(200);
      expect(res2.body).toMatchObject(body);
    });

    it('should successfully create short url use long url 2082', async () => {
      const url = `baidu.com/rauthorzatioauthorizaauthorizan3zation33aut/ization33aut/izatization33aut/ization33aut/ization33aut/ization33aut/ization33aut/ization33authorization33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zh2//232/zation3zhan2//232/zation3zhanang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232`;
      const { body } = await request(app.getServer())
        .post(endpoint)
        .set('Authorization', `Bearer ${token}`)
        .send({
          url: url
        }).expect(200);
      expect(body).toHaveProperty('url');
    });

    it('should failed create short url use long url 2083', async () => {
      const url = `baidu.com/rauthowrzatioauthorizaauthorizan3zation33aut/ization33aut/izatization33aut/ization33aut/ization33aut/ization33aut/ization33aut/ization33authorization33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation33authorization33aut/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zh2//232/zation3zhan2//232/zation3zhanang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/zation3zhang3autho!@#rization33aut/23//23//232//232/on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232on3zhang3autho!@#rization33aut/23//23//232//232`;
      const { body } = await request(app.getServer())
        .post(endpoint)
        .set('Authorization', `Bearer ${token}`)
        .send({
          url: url
        }).expect(400);
      expect(body).toMatchObject({"message": "Invalid URL", "status": 400});
    });

    it('should successfully create short url use different user', async () => {
      const url = 'baidu.com/authorization/test1';
      const request1 = await request(app.getServer())
        .post(endpoint)
        .set('Authorization', `Bearer ${token}`)
        .send({
          url: url
        }).expect(200);
      expect(request1.body).toHaveProperty('url');

      const request2 = await request(app.getServer())
        .post(endpoint)
        .set('Authorization', `Bearer ${token2}`)
        .send({
          url: url
        }).expect(200);
      expect(request2.body).toHaveProperty('url');
      expect(request2.body.url).not.toEqual(request1.body.url);
    });

  });

  describe(`[POST] ${endpoint} in black list`, () => {

    afterAll(async ()=> {
      const redisClient: any = app.redisService.getClient();
      await redisClient.flushAll('SYNC');
    });

    it('should successfully be add in blacklist after many request', async () => {
      const url = 'baidu.com/authorization/blacklist';
      const promises: Promise<any>[] = [];
      for (let i = 0; i < RATE_LIMIT_MAX_REQUESTS+1; i++) {
        promises.push(request(app.getServer())
          .post(endpoint)
          .set('Authorization', `Bearer ${token}`)
          .send({
            url: url
          }));
      }
      const responses = await Promise.all(promises);
      const response = responses.find(response => response.status === 403);
      expect(response).toBeDefined();
      expect(response.status).toBe(403);
      expect(response.body).toMatchObject({ status: 403, message: 'Forbidden.' });
     
     
      const request1 = await request(app.getServer())
        .post(endpoint)
        .set('Authorization', `Bearer ${token}`)
        .send({
          url: url
        }).expect(403);
      expect(request1.body).toMatchObject({"message": "Forbidden.", "status": 403});

      const request2 = await request(app.getServer())
        .post(endpoint)
        .set('Authorization', `Bearer ${token2}`)
        .send({
          url: url
        }).expect(200);
      expect(request2.body).toHaveProperty('url');
    });

  });

});
