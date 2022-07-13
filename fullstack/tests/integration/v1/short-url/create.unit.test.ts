import {  TEST_URL } from '../../../constants';
import supertest from 'supertest';
import app from '../../../../src/app';

describe('Integrition test for create short url api', () => {
  

  const request = supertest(app);
  const endpoint = '/v1/short-url';
  //成功生成短链接
  it('Should send 200 when successfully shortening url', async () => {
    const response = await request.post(endpoint).send({'url':TEST_URL});
    expect(response.status).toBe(200);
    expect(response.body.data.length).toBe(8);
  });

  // url 不合法
  it('Should send 400 when invalid url', async () => {
    const response = await request.post(endpoint).send({'url':'1223'});
    expect(response.status).toBe(400);
    expect(response.body.message).toMatch('Invalid url.');
  });

  // url 为空字符串
  it('Should send 400 when empty url', async () => {
    const response = await request.post(endpoint).send({'url':''});
    expect(response.status).toBe(400);
    expect(response.body.message).toMatch('Url should not be empty.');
  });

  //未添加url
  it('Should send 400 when none url', async () => {
    const response = await request.post(endpoint).send();
    expect(response.status).toBe(400);
    expect(response.body.message).toMatch('Url should not be empty.');
  });
});
