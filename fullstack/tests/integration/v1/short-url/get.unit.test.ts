import {  TEST_URL } from '../../../constants';
import supertest from 'supertest';
import app from '../../../../src/app';
import delay from 'delay';

describe('Integrition test for get short url api', () => {
  

  const request = supertest(app);
  const endpoint = '/v1/short-url';
  // 未传入key
  it('Should send 404 not found when key is not passed', async () => {
    const response = await request.get(endpoint);
    expect(response.status).toBe(404);
  });

  //成功处理传入key， 重定向到对应url
  it('Should send 302 redirect when key is correct', async () => {
    const key = await (await request.post(endpoint).send({'url':TEST_URL})).body.data;
    await delay(200);
    const response = await request.get(endpoint+'/'+key);
    expect(response.status).toBe(302);
    expect(response.headers['location']).toMatch(TEST_URL);
  });

  //合法key， 但是并不存在对应的url
  it('Should send 422 unprocessable when key does not exist', async () => {
    const response = await request.get(endpoint+'/testtest');
    expect(response.status).toBe(422);
    expect(response.body.message).toMatch('Error: Unable to find URL to redirect to.');
  });

  //key的长度不合法
  it('Should send 400 unprocessable when key length is invalid', async () => {
    const response = await request.get(endpoint+'/test');
    expect(response.status).toBe(400);
    expect(response.body.message).toMatch('Invalid key.');
  });

  //key中含有非法的字符
  it('Should send 400 unprocessable when key has invalid characters', async () => {
    const response = await request.get(endpoint+'/tes*t232');
    expect(response.status).toBe(400);
    expect(response.body.message).toMatch('Invalid key.');
  });
});
