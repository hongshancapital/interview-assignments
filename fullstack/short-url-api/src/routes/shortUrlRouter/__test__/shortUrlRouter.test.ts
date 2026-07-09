import request from 'supertest';
import app from 'src/app';
import { connectDb, closeDb } from 'utils/testUtils';
import ResCode from 'src/routes/resCode';
import ShortUrl from 'src/schemas/ShortUrl';
import ShortUrlController from 'controllers/shortUrlController';

const mockLongUrl =
  'https://baidu.com/dailyjs/a-simple-guide-to-understanding-javascript-es6-generators-d1c350551950';

describe('shortUrl route tests', () => {
  beforeEach(async () => {
    await connectDb();
  });

  afterEach(async () => {
    await closeDb();
  });

  // create testing
  it('should get empty string if long url is empty or not set', async () => {
    const res = await request(app).post('/api/shortUrl').send();
    expect(res?.status).toEqual(200);
    expect(res?.body?.data).toBeNull();
    expect(res?.body?.code).toEqual(ResCode.InvalidData);

    const res2 = await request(app).post('/api/shortUrl').send({ longUrl: '' });
    expect(res2?.status).toEqual(200);
    expect(res2?.body?.data).toBeNull();
    expect(res2?.body?.code).toEqual(ResCode.InvalidData);
  });

  it('should not create a new doc if set an invalid url', async () => {
    const res = await request(app)
      .post('/api/shortUrl')
      .send({ longUrl: 'httpss://www.invalid.com/' });
    expect(res.status).toEqual(200);
    expect(res?.body?.data).toBeNull();
    expect(res?.body?.code).toEqual(ResCode.InvalidData);
  });

  it('should create a new short url', async () => {
    const res = await request(app).post('/api/shortUrl').send({ longUrl: mockLongUrl });
    expect(res.status).toEqual(200);
    expect(res?.body?.data?.shortUrl).toHaveLength(8);
  });

  it('should throw error', async () => {
    const err = { message: 'create failed' };
    jest.spyOn(ShortUrl, 'create').mockImplementationOnce(() => Promise.reject(err));
    const res = await request(app).post('/api/shortUrl').send({ longUrl: mockLongUrl });
    expect(res.status).toEqual(200);
    expect(res.body?.error).toMatchObject(err);
  });

  it('should get an empty object', async () => {
    jest.spyOn(ShortUrlController, 'create').mockImplementationOnce(() => null);
    const res = await request(app).post('/api/shortUrl').send({ longUrl: mockLongUrl });
    expect(res.status).toEqual(200);
    expect(res.body?.data).toBeNull();
  });

  it('should check and get data from cache', async () => {
    const res = await request(app).post('/api/shortUrl').send({ longUrl: mockLongUrl });
    expect(res.status).toEqual(200);
    expect(res?.body?.data?.shortUrl).toHaveLength(8);
    const res2 = await request(app).post('/api/shortUrl').send({ longUrl: mockLongUrl });
    expect(res2.status).toEqual(200);
    expect(res?.body?.data).toMatchObject(res2.body.data);
  });

  it('should create a new short url with fixed length', async () => {
    const res = await request(app).post('/api/shortUrl').send({ longUrl: mockLongUrl, length: 12 });
    expect(res.status).toEqual(200);
    expect(res?.body?.data?.shortUrl).toHaveLength(12);
  });

  // get testing
  it('should get empty long url', async () => {
    const res = await request(app).get(`/api/shortUrl?url=abc12wda`);
    expect(res.status).toEqual(200);
    expect(res.body?.data).toBeNull();
  });

  it('should get short url', async () => {
    const doc = await ShortUrlController.create(mockLongUrl);
    expect(doc?.shortUrl).toBeDefined();

    const res = await request(app).get(`/api/shortUrl?url=${doc.shortUrl}`);
    expect(res.status).toEqual(200);
    expect(res.body?.data?.longUrl).toEqual(mockLongUrl);
  });

  it('should get short url in cache', async () => {
    const res = await request(app).post('/api/shortUrl').send({ longUrl: mockLongUrl });
    expect(res.status).toEqual(200);
    expect(res.body?.data?.shortUrl).toHaveLength(8);

    const res2 = await request(app).get(`/api/shortUrl?url=${res.body.data.shortUrl}`);
    expect(res2.status).toEqual(200);
    expect(res.body.data).toMatchObject(res2.body.data);
  });

  it('should get error object', async () => {
    const err = { message: 'not exist' };
    jest
      .spyOn(ShortUrlController, 'findByShortUrl')
      .mockImplementationOnce(() => Promise.reject(err));
    const res = await request(app).get('/api/shortUrl?url=abc');
    expect(res.status).toEqual(200);
    expect(res.body?.data).toBeNull();
    expect(res.body?.error).toMatchObject(err);
  });
});
