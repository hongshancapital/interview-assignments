import { Context, Subscription } from 'egg';
import UrlMapDao from '../dao/url-map';
import UrlMapRedis from '../redis/url-map';
import UrlMapCache from '../service/cache';
import { UrlMapStateEnum } from '../utils/enum';

export default class UpdateUrlMap extends Subscription {
  urlMapDao: UrlMapDao;
  urlMapRedis: UrlMapRedis;
  urlMapCache: UrlMapCache;

  constructor(ctx: Context) {
    super(ctx);
    this.urlMapRedis = this.app.redis.urlMap;
    this.urlMapDao = this.ctx.dao.urlMap;
    this.urlMapCache = this.ctx.service.cache;
  }

  static get schedule() {
    return {
      // 每天
      interval: '24h',
      // 随机指定一个 worker 去执行定时任务
      type: 'worker',
    };
  }


  // subscribe 是真正定时任务执行时被运行的函数
  async subscribe() {
    const urlMapMDatas = await this.urlMapDao.getExpiredMDatas();
    if (urlMapMDatas.length) {
      const tinyUrls = urlMapMDatas.map(item => item.tinyUrl);
      await this.urlMapDao.updateDataById(urlMapMDatas.map(item => item.id), { state: UrlMapStateEnum.Abnormal });
      await this.urlMapRedis.del(tinyUrls);
      this.urlMapCache.del(tinyUrls);
    }
  }
}
