import { describe, expect, test, afterAll, jest } from '@jest/globals';
import request from 'supertest';
import { app, server } from '../app/app';

import { SHORT_DOMAIN } from '../app/common/url';
import { SUCCESS, NOT_FOUND, DB_RUNTIME_ERROR, PARAM_ERROR } from '../app/common/errCode';

import * as db from '../app/database/db';


const fetchAPI = async (url: string, method: string) => {
  let res: any;

  if (method === 'get') {
    res = await request(app).get('/api/shorturl').query({ url });
  } else {
    res = await request(app).post('/api/shorturl').send({ url });
  }

  expect(res.status).toBe(200);

  return res.body;
}

afterAll(() => { server.close() });

describe('API IT', () => {
  test('get by a empty url', async () => {
    const resData = await fetchAPI('', 'get');

    expect(resData.errCode).toBe(PARAM_ERROR);
  });

  test('get by a null url', async () => {
    const resData = await fetchAPI(SHORT_DOMAIN + 'abcde', 'get');

    expect(resData.errCode).toBe(NOT_FOUND);
  });


  let shortUrl: string = '';
  const originUrl = 'https://www.baidu.com/s?' + Date.now();

  jest.mock('../app/database/db');
  jest.spyOn(db, 'insert').mockResolvedValueOnce(DB_RUNTIME_ERROR);
  test('error occured while posting', async () => {

    const resData = await fetchAPI(originUrl, 'post');

    expect(resData.errCode).toBe(DB_RUNTIME_ERROR);
  });

  test('post a new url', async () => {
    const resData = await fetchAPI(originUrl, 'post');

    expect(resData.errCode).toBe(SUCCESS);
    shortUrl = resData.data.url;
    expect(shortUrl).not.toBe('');
  });

  test('post a same url', async () => {
    const resData = await fetchAPI(originUrl, 'post');

    expect(resData.errCode).toBe(SUCCESS);
    expect(resData.data.url).toBe(shortUrl);
  });

  test('get short url by posted url', async () => {
    const resData = await fetchAPI(shortUrl, 'get');

    expect(resData.errCode).toBe(SUCCESS);
    expect(resData.data.url).toBe(originUrl);
  });
});