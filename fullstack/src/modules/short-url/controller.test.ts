/*
 * @Author: Json Chen
 * @Date: 2023-02-13 18:22:44
 * @LastEditors: Json Chen
 * @LastEditTime: 2023-02-13 19:06:14
 * @FilePath: /interview-assignments/fullstack/src/modules/short-url/controller.test.ts
 */

import request from 'supertest';
import shortUrlController from './controller';

describe('ShortUrlController', () => {
  it('creates short URL', async () => {
    const response = await request(shortUrlController)
      .post('/short-urls')
      .send({ url: 'https://www.example.com/long-url' });

    expect(response.status).toBe(200);
    expect(response.body).toHaveProperty('shortId');
  });
});


describe('ShortUrlRouter', () => {
  it('redirects to long URL', async () => {
    const response = await request(shortUrlController).get('/abcdefg');

    expect(response.status).toBe(302);
    expect(response.header.location).toBe('https://www.example.com/long-url');
  });
});