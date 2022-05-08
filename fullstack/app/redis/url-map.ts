import { Application } from 'egg';
import { IUrlMapCache } from 'interface';

export default class UrlMapRedis {
  app: Application;

  constructor(app: Application) {
    this.app = app;
  }

  async get(key: string): Promise<IUrlMapCache> {
    const redisValue = await this.app.redisClient.get(key);
    return redisValue ? JSON.parse(redisValue) : null;
  }

  async set(key: string, value: IUrlMapCache) {
    const redisValue = JSON.stringify(value);
    return await this.app.redisClient.set(key, redisValue);
  }

  async del(key: string | string[]) {
    if (this.app.redisClient.get(key)) {
      await this.app.redisClient.del(key);
    }
  }
}
