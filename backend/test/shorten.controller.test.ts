import request from 'supertest';
import assert from 'assert';

import { app } from '../src/app';

const urls = [
  {
    longUrl: 'www.baidu.com/xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxlllllllllllllllllllllllllllllllllkkkkkkkkkkkkkkkkk',
    shortUrl: 'CS7xS4'
  }
]

describe('ShortenController test suits', () => {  
  it.skip('可以正常请求生成短链', async () => {
    const {longUrl, shortUrl} = urls[0];
    await request(app)
    .post('/shorten').send({originalUrl: longUrl})
    .expect(200)
    .then(resp => {
      assert(resp.body.data.shortUrl, shortUrl)
    });
  })
  
  it.skip('可以正常获取到对应的原始链接', async () => {
    const {longUrl, shortUrl} = urls[0];
    await request(app)
      .get('/shorten/original/' + shortUrl)
      .expect(200)
      .then(resp => {
        assert(resp.body.data.originalUrl, longUrl)
      })
  })
})