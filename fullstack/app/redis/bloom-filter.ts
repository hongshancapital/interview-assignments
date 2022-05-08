import { Application } from 'egg';
import { BLOOM_REDIS_KEY } from '../utils/constants';

export default class BloomFilterRedis {
  app: Application;

  constructor(app: Application) {
    this.app = app;
  }

  async get(): Promise<any> {
    const redisValue = await this.app.redisClient.get(BLOOM_REDIS_KEY);
    return redisValue ? JSON.parse(redisValue) : null;
  }

  async set(value: any) {
    const redisValue = JSON.stringify(value);
    return await this.app.redisClient.set(BLOOM_REDIS_KEY, redisValue);
  }
}
