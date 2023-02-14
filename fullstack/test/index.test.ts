import supertest from 'supertest';
import server from '../src/app';
import { nanoid } from 'nanoid';

const createShortUrl = (longUrl?: string) =>
  supertest(server).post('/longUrl').set('Content-Type', 'application/json').send({ longUrl });

const getLongUrl = (shortUrl?: string) => supertest(server).get(`/shortUrl?shortUrl=${shortUrl}`);

describe('test short url service', () => {
  const longUrl = 'http://www.short.com/create-longurl-from-hubiao';
  test('create short url from not validate url', async () => {
    const res = await createShortUrl('not validate url');
    expect(res.status).toEqual(401);
  });

  test('get long url no exist', async () => {
    const res = await getLongUrl('http://www.short.com/no-exist-shorturl');
    expect(res.status).toEqual(404);
  });

  test('create short from validate url, and get long url exist', async () => {
    const createRes = await createShortUrl(longUrl);
    const { shortUrl } = createRes.body;
    const longRes = await getLongUrl(shortUrl);
    expect(longRes.body.longUrl).toEqual(longUrl);
  });

  test('shorturl length is 8', async () => {
    const shortUrlId = nanoid(8);
    expect(shortUrlId).toHaveLength(8);
  });
});
