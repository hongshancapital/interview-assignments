import { createApp, close, createHttpRequest } from '@midwayjs/mock';
import { Framework } from '@midwayjs/koa';

describe('test/controller/home.test.ts', () => {

  it('should GET /api/makeShotUrl', async () => {
    // create app
    const app = await createApp<Framework>();

    // make request
    const result = await createHttpRequest(app).get('/api/makeShotUrl').query({ long_url: "http://www/aafaaa/cpm/aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa123" });

    // use expect by jest
    expect(result.status).toBe(200);
    expect(result.body.message).toBe('OK');

    // close app
    await close(app);
  });
});
