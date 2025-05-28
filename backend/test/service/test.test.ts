/**
 * @file model service 单例测试
 * @author zengbaoqing<misterapptracy@gmail.com>
 */
import { app, assert } from 'egg-mock/bootstrap';

describe('create()', () => {
  it('should return model data after create', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const data = await ctx.service.test.create({ content: `test:${+new Date()}` });
    assert(data && data.id);
  });
});

describe('bulkCreate()', () => {
  it('should return model datas after create', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const values: { content: string }[] = [];
    for (let i = 0; i < 10; i += 1) {
      values.push({ content: `test:${+new Date() + i}` });
    }
    const data = await ctx.service.test.bulkCreate(values);
    assert(data && data.length === 10 && data[0].id);
  });
});

describe('getOne()', () => {
  it('should throw error with empty where option ', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    try {
      await ctx.service.test.getOne({});
      assert(true);
    } catch (e) {
      assert(e);
    }
  });

  it('should return null with not exits id', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const data = await ctx.service.test.getOne({ where: { id: -1 } });
    assert(data === null);
  });

  it('should return object with exits id', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const { id } = await ctx.service.test.create({ content: `test:${+new Date()}` });
    const data = await ctx.service.test.getOne({ where: { id } });
    assert(data && data.content);
  });
});

describe('getAll()', () => {
  it('should throw error with empty where option ', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    try {
      await ctx.service.test.getAll({});
      assert(true);
    } catch (e) {
      assert(e);
    }
  });

  it('should return [] with not exits id', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const data = await ctx.service.test.getAll({ where: { id: -1 } });
    assert(!data.length);
  });

  it('should return array with exits id', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const { id } = await ctx.service.test.create({ content: `test:${+new Date()}` });
    const data = await ctx.service.test.getAll({ where: { id } });
    assert(data && data.length === 1 && data[0].content);
  });
});

describe('update()', () => {

  it('should 0 modified with not exits id', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const [modified] = await ctx.service.test.update({ content: `test:${+new Date()}` }, { where: { id: -1 } });
    assert(!modified);
  });

  it('should modified with exits id', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    const values: { content: string }[] = [];
    for (let i = 0; i < 10; i += 1) {
      values.push({ content: `test:${+new Date() + i}` });
    }
    await ctx.service.test.bulkCreate(values);
    const [modified] = await ctx.service.test.update({ content: `test:${+new Date()}` }, { where: { id: { $gte: 0 } } });
    assert(modified >= 10);
  });
});