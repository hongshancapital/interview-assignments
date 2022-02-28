import request from 'supertest';
import { app } from '../src/app';
import { redis } from '../src/libs/redis-connection';
import { sequelize } from '../src/libs/db-connection';

let tmpShort: string = '';

beforeAll(async () => {
  await sequelize.models.shortUrlModel.create({
    longUrl: 'http://www.baidu.com',
  });
});
describe('GET /:shortUrl, get the original long url of a given shortUrl', () => {
  it('get an invalid shortUrl', async () => {
    const res = await request(app)
      .get('/sfj*kf')
      .set('Accept', 'application/json');

    expect(res.body.errMsg).toBe('server received an invalid short url.');
    expect(res.body.success).toBe(false);
  });
  it('get a non-existing shortUrl', async () => {
    const res = await request(app)
      .get('/noexist')
      .set('Accept', 'application/json');

    expect(res.body.data.longUrl).toBe(undefined);
    expect(res.body.success).toBe(true);
  });

  it('get a normal shortUrl without cache', async () => {
    await redis.del('b');
    const res = await request(app).get('/b').set('Accept', 'application/json');
    console.log(res.body);
    expect(res.body.success).toBe(true);
    expect(res.body.data).toHaveProperty('longUrl');
  });

  it('get a normal shortUrl with cache', async () => {
    const res = await request(app).get('/b').set('Accept', 'application/json');
    expect(res.body.success).toBe(true);
    expect(res.body.data).toHaveProperty('longUrl');
  });
});

describe('POST /addUrl, add the original long url to get a shortUrl', () => {
  it('post an invalid longUrl', async () => {
    const res = await request(app)
      .post('/addUrl')
      .set('Accept', 'application/json')
      .send({
        longUrl: 'not-a-correct-url',
      });

    expect(res.body.success).toBe(false);
  });

  it('post a normal longUrl', async () => {
    const res = await request(app)
      .post('/addUrl')
      .set('Accept', 'application/json')
      .send({
        longUrl: 'https://www.google.com?search=good',
      });

    tmpShort = res.body.data.shortUrl;

    expect(res.body.success).toBe(true);
    expect(res.body.data).toHaveProperty('shortUrl');
  });

  it('post a normal longUrl again should return cache', async () => {
    const res = await request(app)
      .post('/addUrl')
      .set('Accept', 'application/json')
      .send({
        longUrl: 'https://www.google.com?search=good',
      });

    expect(res.body.success).toBe(true);
    expect(res.body.data).toHaveProperty('shortUrl');
    expect(res.body.data.shortUrl).toEqual(tmpShort);
  });
});

afterAll(async () => {
  await sequelize.close();
  await redis.disconnect();
});
