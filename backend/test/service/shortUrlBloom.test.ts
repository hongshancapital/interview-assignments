/**
 * @file service 单例测试
 * @author zengbaoqing<misterapptracy@gmail.com>
 */

import { app, assert } from 'egg-mock/bootstrap';

describe('add()', () => {

  it('should return undefined after added', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const str = `${+new Date()}`;
    const setResult = await ctx.service.shortUrlBloom.add(str);
    assert(setResult === undefined);
  });
});

describe('contains()', () => {
  it('should return false with empty string', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const hasContained = await ctx.service.shortUrlBloom.contains('');
    assert(!hasContained);
  });

  it('should return false before added', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const hasContained = await ctx.service.shortUrlBloom.contains(`${+new Date()}`);
    assert(!hasContained);
  });

  it('should return true after added', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const str = `${+new Date()}`;
    await ctx.service.shortUrlBloom.add(str);
    const hasContained = await ctx.service.shortUrlBloom.contains(str);
    assert(hasContained);
  });
});
