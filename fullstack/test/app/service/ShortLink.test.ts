import assert from 'assert';
import { Context } from 'egg';
import { app } from 'egg-mock/bootstrap';

describe('test/app/service/ShortLink.test.js', () => {
  let ctx: Context;

  before(async () => {
    ctx = app.mockContext();
  });

  it('checkUrl(\'https://cn.bing.com/\') should true', async () => {
    const url = 'https://cn.bing.com/';
    const result = await ctx.service.shortLink.checkUrl(url);
    assert(result === true);
  });

  it('checkUrl(\'https/cn.bing.com/\') should false', async () => {
    const url = 'https/cn.bing.com/';
    const result = await ctx.service.shortLink.checkUrl(url);
    assert(result === false);
  });
});
