import supertest from 'supertest';
import { disconnect } from 'mongoose';

import app from '../src/app';

describe('POST /shorturl/create', () => {
  it('no url parameter passed', async () => {
    return await supertest(app)
      .post('/shorturl/create')
      .send({
        url1: 'https://www.baidu.com',
      })
      .expect(400)
      .then((res) => {
        expect(res.body).toMatchObject({ data: 'url为必传字段' });
      });
  });

  it('should url return message success', async () => {
    return await supertest(app)
      .post('/shorturl/create')
      .send({
        url: 'https://www.baidu.com',
      })
      .expect(200)
      .then((res) => {
        expect(res.body).toMatchObject({ message: 'success' });
      });
  });
});

describe('GET /longurl/get', () => {
  it('id is a required field', async () => {
    return await supertest(app)
      .get('/longurl/get?iddd=123')
      .expect(400)
      .then((res) => {
        expect(res.body).toMatchObject({ data: 'id为必传字段' });
      });
  });

  it('id is a required field', async () => {
    return await supertest(app)
      .get('/longurl/get?id=wuxiaoid123')
      .expect(400)
      .then((res) => {
        expect(res.body).toMatchObject({ data: '无效id' });
      });
  });

  it('long url return message success', async () => {
    return await supertest(app)
      .get('/longurl/get?id=1dSXmNl')
      .expect(200)
      .then((res) => {
        expect(res.body).toMatchObject({ message: 'success' });
      });
  });

  it('long url with cache return message success', async () => {
    await supertest(app).get('/longurl/get?id=1dSXmNl');

    return await supertest(app)
      .get('/longurl/get?id=1dSXmNl')
      .expect(200)
      .then((res) => {
        expect(res.body).toMatchObject({ message: 'success' });
      });
  });
});
