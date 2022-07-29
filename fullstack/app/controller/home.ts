import { Controller } from 'egg';

export default class shortLinkController extends Controller {
  public async index() {
    const { ctx, app } = this;
    const { params } = ctx;

    if (!params.key) {
      return;
    }
    const url = await app.redis.get(ctx.service.shortLink.keyPrefix + params.key);
    if (url) {
      ctx.redirect(url);
    }
  }

  public async create() {
    const { ctx } = this;
    const { query } = ctx;
    let key: string;

    if (!query.url) {
      ctx.body = {
        status: false,
        msg: 'url参数不能为空',
      };
      return;
    }
    // check url
    if (!ctx.service.shortLink.checkUrl(query.url)) {
      ctx.body = {
        status: false,
        msg: 'url不合法',
      };
      return;
    }
    // 是否已经存在这个url的短链接key
    const hasKey = await this.app.redis.get(ctx.service.shortLink.originUrlPrefix + query.url);
    if (hasKey) {
      key = hasKey;
    } else {
      // 不存在则随机生成
      key = await ctx.service.shortLink.getRandomKey();
      // 并把随机生成的数据 作为双向key-value 存进redis
      await Promise.all([
        this.app.redis.set(ctx.service.shortLink.keyPrefix + key, query.url),
        this.app.redis.set(ctx.service.shortLink.originUrlPrefix + query.url, key),
      ]);
    }
    ctx.body = {
      status: true,
      data: { key },
    };
  }
}
