/**
 * @file 短链接api controller
 * @author zengbaoqing<misterapptracy@gmail.com>
 */

import { Controller } from 'egg';
import baseError from '../core/base/baseError';

export default class ShortUrl extends Controller {

  public async create() {
    const { ctx, service } = this;
    const url: string = ctx.request.body.url;
    if (!url || typeof url !== 'string' || url.length > 2047 || !/.*?((?:http|https)(?::\/{2}[\w]+)(?:[\/|\.]?)(?:[^\s"]*)).*?/i.test(url)) {
      return ctx.body = baseError.paramsError();
    }
    let shortId = '';
    let repeated = true;
    // nanoid生成需要和过滤器中已有id比对，如果碰撞需要重新生成
    while (repeated) {
      shortId = await service.shortId.getNewShortId();
      // 真实的布隆过滤器查询有一定概率的存在误判，但不存在的判断是肯定的
      repeated = await service.shortUrlBloom.contains(shortId);
    }
    // 优先保证过滤器的数据存储，在极端情况下，过滤器中的数据存储成功，db的数据存储失败，此次请求为失败请求，此shortId不会面向到用户， 由此导致的布隆过滤器数据存量大于db数据也是可以接受的
    await service.shortUrlBloom.add(shortId);
    await service.shortUrl.create({ shortId, url });
    // 一旦db数据存储成功，此步骤的缓存数据在极小的概率下存储失败也不影响
    ctx.runInBackground(async () => {
      await service.shortUrlCache.setData(shortId, url);
    });
    ctx.body = {
      status: 0,
      data: {
        shortId
      },
    };
  }

  public async redirect() {
    const { ctx, service } = this;
    const shortId: string = ctx.params.shortId;
    const { maxIdLen } = service.shortId;
    if (!shortId || !shortId.length || shortId.length > maxIdLen) {
      return ctx.status = 404;
    }
    // 先从过滤器中寻找，防止恶意攻击导致的缓存的穿透
    const hasContained = await service.shortUrlBloom.contains(shortId);
    // 真实的布隆过滤器判断不存在是可信的
    if (!hasContained) {
      return ctx.status = 404;
    }
    // 从缓存数据中寻找，如果不存在，再访问db
    const cacheUrl = await service.shortUrlCache.getData(shortId);
    if (cacheUrl) {
      // 刷新短连接缓存的过期时间
      ctx.runInBackground(async () => {
        service.shortUrlCache.refresh(shortId);
      });
      return ctx.redirect(cacheUrl);
    }
    const dbData = await service.shortUrl.getOne({ where: { shortId }, attributes: ['url'] });
    if (dbData && dbData.url) {
      ctx.runInBackground(async () => {
        service.shortUrlCache.setData(shortId, dbData.url);
      });
      return ctx.redirect(dbData.url);
    }
    ctx.status = 404;
  }
}
