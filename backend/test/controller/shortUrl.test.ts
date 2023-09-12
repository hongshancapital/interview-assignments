/**
 * @file api测试
 * @author zengbaoqing<misterapptracy@gmail.com>
 */

import { app, assert } from 'egg-mock/bootstrap';

describe('test/controller/shortUrl.test.js', () => {

  describe('POST /create', () => {

    it('bad request with empty url', async () => {
      const result = await app.httpRequest().post('/create').send({ url: '' });
      assert(result.status === 200);
      assert(result.body && result.body.status === 1001);
    });

    it('bad request with invaild url', async () => {
      const result = await app.httpRequest().post('/create').send({ url: 'www.baidu.com' });
      assert(result.status === 200);
      assert(result.body && result.body.status === 1001);
    });

    it('bad request with too long url', async () => {
      let url = 'https://www.baidu.com/';
      for (let i = 0; i < 2036; i += 1) {
        url += 'a';
      }
      const result = await app.httpRequest().post('/create').send({ url });
      assert(result.status === 200);
      assert(result.body && result.body.status === 1001);
    });

    it('good request with vaild url', async () => {
      const ctx = app.mockContext();
      const result = await app.httpRequest().post('/create').send({ url: 'https://www.baidu.com/' });
      assert(result.status === 200);
      assert(result.body && result.body.data && result.body.status === 0);
      const shortId: string = result.body.data.shortId;
      const { charset, maxIdLen } = ctx.service.shortId;
      if (typeof shortId !== 'string'! || shortId.length !== maxIdLen) {
        assert(false);
      }
      for (let i = 0; i < shortId.length; i += 1) {
        if (charset.indexOf(shortId[i]) === -1) {
          assert(false);
        }
      }
    });

  });

  describe('GET /', () => {
    it('bad request with empty shortId', async () => {
      const result = await app.httpRequest().get('/');
      assert(result.status === 404);
    });

    it('bad request with invaild shortId', async () => {
      const result = await app.httpRequest().get('/longshortId');
      assert(result.status === 404);
    });

    it('bad request with not exist shortId', async () => {
      const ctx = app.mockContext();
      const nanoid = await ctx.service.shortId.getNewShortId();
      const result = await app.httpRequest().get(`/${nanoid}`);
      assert(result.status === 404);
    });

    it('bad request with exist shortId', async () => {
      const { body } = await app.httpRequest().post('/create').send({ url: 'https://www.baidu.com/' });
      assert(body && body.data && body.data.shortId);
      const result = await app.httpRequest().get(`/${body.data.shortId}`);
      assert(result.status === 302);
    });

  });
});