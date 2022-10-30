import Redis from "ioredis";

/**
 * 缓存类
 */
export class Cache {
  private client: Redis;

  constructor(redis: Redis, keyPrefix: string = '') {
    this.client = redis;
  }

  get(key: string): Promise<string | null> {
    return this.client.get(key);
  }

  set(key: string, value: string, seconds: number): Promise<string> {
    return this.client.setex(key, seconds, value);
  }

  remove(key: string): Promise<number> {
    return this.client.del(key);
  }
}
