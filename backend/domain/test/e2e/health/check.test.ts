import "reflect-metadata";

import request from 'supertest';
import app from '../../../src/server';

describe('Health check', () => {
  const endpoint = '/ping';

  beforeAll(async ()=> {
    await app.listen();
  });

  afterAll(async ()=> {
    await app.redisService.disconnect();
    await app.mongoService.disconnect();
    await app.getServer().close();
  });

  describe(`[GET] ${endpoint}`, () => {

    it('should return pong ', async () => {
      await app.redisService.connect();
      await app.mongoService.connect();
      const { body } = await request(app.getServer())
        .get(endpoint)
        .expect(200);
      expect(body).toEqual('pong');
    });

    it('should return false', async () => {
      await app.mongoService.disconnect();
      const { body } = await request(app.getServer())
        .get(endpoint)
        .expect(500);
      expect(body).toEqual('false');
    });

    it('should failed 404', async () => {
      await app.mongoService.disconnect();
      await request(app.getServer())
        .post(`/error`)
        .expect(404);
    });
   
  });
});
