import { Application } from 'egg';
import { IRefreshBloom, IRefreshCache } from 'interface';
import { createClient } from 'redis';
import LRVCache from './app/utils/lru-cache';
import { BloomFilter } from 'bloom-filters';
import { BLOOM_PROCESS_KEY, CACHE_PROCESS_KEY } from './app/utils/constants';

export default class AppBootHook {
  public app: Application;
  constructor(app: Application) {
    this.app = app;
  }

  async didLoad() {
    // 所有的配置已经加载完毕
    // 可以用来加载应用自定义的文件，启动自定义的服务
    await this.initRedis();
  }

  async didReady() {
    // 应用已经启动完毕
    this.initCache();
    await this.bloomFilter();
  }

  initCache() {
    // TODO 这里可以根据需要来配置缓存的数量
    this.app.cache = LRVCache(100);
    // 多进程通信
    this.app.messenger.on(CACHE_PROCESS_KEY, (data: IRefreshCache) => {
      if (process.pid !== data.pid) {
        const ctx = this.app.createAnonymousContext();
        ctx.runInBackground(async () => {
          await ctx.service.cache.update(data);
        });
      }
    });
  }

  async initRedis() {
    const { redisConfig } = this.app.config;
    const client = createClient(redisConfig);
    client.on('error', (err: any) => console.log('Redis Client Error', err));
    await client.connect();
    this.app.redisClient = client;
  }

  async bloomFilter() {
    const exported = await this.app.redis.bloomFilter.get();
    this.app.bloomFilter = exported ?
      BloomFilter.fromJSON(exported) :
      // TODO 可以根据需要设置
      new BloomFilter(10, 4);
    // 多进程通信
    this.app.messenger.on(BLOOM_PROCESS_KEY, (data: IRefreshBloom) => {
      if (process.pid !== data.pid) {
        const ctx = this.app.createAnonymousContext();
        ctx.runInBackground(async () => {
          ctx.service.bloomFilter.update(data.value);
        });
      }
    });
  }
}
