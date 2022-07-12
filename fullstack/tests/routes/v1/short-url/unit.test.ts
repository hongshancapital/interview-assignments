import {  TEST_URL } from '../../../constants';
import supertest from 'supertest';
import app from '../../../../src/app';
import delay from 'delay';

describe('Integrition test for short url api', () => {
  beforeEach(() => {
  });

  const request = supertest(app);
  const endpoint = '/v1/short-url';

  it('Should send 200 when successfully shortening url', async () => {
    const response = await request.post(endpoint).send({'url':TEST_URL});
    expect(response.status).toBe(200);
    expect(response.body.data.length).toBe(8);
  });
  it('Should send 400 when invalid url', async () => {
    const response = await request.post(endpoint).send({'url':'1223'});
    expect(response.status).toBe(400);
    expect(response.body.message).toMatch('Invalid url.');
  });

  it('Should send 400 when empty url', async () => {
    const response = await request.post(endpoint).send({'url':''});
    expect(response.status).toBe(400);
    expect(response.body.message).toMatch('Url should not be empty.');
  });

  it('Should send 400 when none url', async () => {
    const response = await request.post(endpoint).send();
    expect(response.status).toBe(400);
    expect(response.body.message).toMatch('Url should not be empty.');
  });
  it('Should send 404 not found when key is not passed', async () => {
    const response = await request.get(endpoint);
    expect(response.status).toBe(404);
  });

  it('Should send 302 redirect when key is correct', async () => {
    const key = await (await request.post(endpoint).send({'url':TEST_URL})).body.data;
    await delay(200);
    const response = await request.get(endpoint+'/'+key);
    expect(response.status).toBe(302);
    expect(response.headers['location']).toMatch(TEST_URL);;
  });

  it('Should send 422 unprocessable when key does not exist', async () => {
    const response = await request.get(endpoint+'/testtest');
    expect(response.status).toBe(422);
    expect(response.body.message).toMatch('Error: Unable to find URL to redirect to.');
  });

  it('Should send 400 unprocessable when key is invalid', async () => {
    const response = await request.get(endpoint+'/test');
    expect(response.status).toBe(400);
    expect(response.body.message).toMatch('Invalid key.');
  });

});
