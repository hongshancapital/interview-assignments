/**
 * @file service 单例测试
 * @author zengbaoqing<misterapptracy@gmail.com>
 */

import { app, assert } from 'egg-mock/bootstrap';

describe('getNewShortId()', () => {
  it('should always return string ', async () => {
    // 创建 ctx
    const ctx = app.mockContext();
    // 通过 ctx 访问到 service.user 
    const id = await ctx.service.shortId.getNewShortId();
    const { charset, maxIdLen } = ctx.service.shortId;
    if (typeof id !== 'string'! || id.length !== maxIdLen) {
      assert(false);
    }
    for (let i = 0; i < id.length; i += 1) {
      if (charset.indexOf(id[i]) === -1) {
        assert(false);
      }
    }
  });

});
