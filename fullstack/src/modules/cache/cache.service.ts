import Redis from "ioredis";
import { config } from "../../config";

export class CacheService {
  constructor(readonly model: Redis, readonly ttl: number) {}

  async set(alias: string, url: string, ttl = this.ttl) {
    await this.model.set(alias, url, "PX", ttl);
  }

  async get(alias: string) {
    const url = await this.model.get(alias);

    return url;
  }
}

let instance: CacheService | undefined;

export function getCacheService() {
  if (!instance) {
    const redis = new Redis(config.CACHE_REDIS_URI);
    instance = new CacheService(redis, config.TTL);
  }
  return instance;
}
