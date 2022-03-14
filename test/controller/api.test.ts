import { createApp, close, createHttpRequest } from '@midwayjs/mock';
import { Framework } from '@midwayjs/koa';

describe('test/controller/home.test.ts', () => {

  it('should Url', async () => {
    // create app
    const app = await createApp<Framework>();

    // health check
    const healthCheckRes = await createHttpRequest(app).get('/');
    expect(healthCheckRes.status).toBe(200);
    expect(healthCheckRes.text).toBe('Hello Midwayjs!');

    // test params
    const result = await createHttpRequest(app).get('/api/getByShortUrl').query({ url: '' });
    expect(result.status).toBe(200);
    expect(result.body.code).toBe(10001);

    // test exist
    const res2 = await createHttpRequest(app).get('/api/getByShortUrl').query({ url: 'https://withnate.cn/o9' });
    expect(res2.status).toBe(200);
    expect(res2.body.code).toBe(0);

    // test create
    const createRes = await createHttpRequest(app).post('/api/createByLongUrl').send({ url: 'https://www.fff.com' });
    expect(createRes.status).toBe(200);
    console.log(JSON.stringify(createRes, null, 4))
    expect(createRes.body.code).toBe(0);

    // close app
    await close(app);
  });

});
