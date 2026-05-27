/**
 * @file service 单例测试
 * @author zengbaoqing<misterapptracy@gmail.com>
 */
import { app, assert } from 'egg-mock/bootstrap';

describe('setData()', () => {

  it('should return undefined after set', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const nanoid = await ctx.service.shortId.getNewShortId();
    const url = `https://www.baidu.com/${+new Date()}`;
    const setResult = await ctx.service.shortUrlCache.setData(nanoid, url);
    assert(setResult === undefined);
  });
});


describe('getData()', () => {
  it('should return null with empty string', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const cacheData = await ctx.service.shortUrlCache.getData('');
    assert(cacheData === null);
  });

  it('should return null before cache added', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const nanoid = await ctx.service.shortId.getNewShortId();
    const cacheData = await ctx.service.shortUrlCache.getData(nanoid);
    assert(cacheData === null);
  });

  it('should return string after cache added', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const nanoid = await ctx.service.shortId.getNewShortId();
    const url = `https://www.baidu.com/${+new Date()}`;
    await ctx.service.shortUrlCache.setData(nanoid, url);
    const cacheData = await ctx.service.shortUrlCache.getData(nanoid);
    assert(cacheData === url);
  });
});

describe('refresh()', () => {

  it('should return false with refreshing not exits string', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const refreshed = await ctx.service.shortUrlCache.refresh(`${+new Date()}`)
    assert(!refreshed);
  });

  it('should return true with refreshing exits string', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const nanoid = await ctx.service.shortId.getNewShortId();
    const url = `https://www.baidu.com/${+new Date()}`;
    await ctx.service.shortUrlCache.setData(nanoid, url);
    const refreshed = await ctx.service.shortUrlCache.refresh(nanoid)
    assert(refreshed);
  });
});
