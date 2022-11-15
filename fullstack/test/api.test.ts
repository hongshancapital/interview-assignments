import supertest from 'supertest';

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

  it('should return message success', async () => {
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

describe('GET /longurl/get/:id', () => {
  it('id is a required field', async () => {
    return await supertest(app)
      .get('http://127.0.0.1:3000/longurl/get')
      .expect(400)
      .then((res) => {
        expect(res.body).toMatchObject({ data: 'id为必传字段' });
      });
  });
});
