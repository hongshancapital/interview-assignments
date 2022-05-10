import { CacheModule, CACHE_MANAGER, Inject, Module, OnApplicationShutdown } from '@nestjs/common';
import { Cache } from 'cache-manager';
import * as RedisStore from 'cache-manager-redis-store';
import type { RedisClientOptions } from 'redis';
import { KvstoreService } from './kvstore.service';
import redisConfig from './redisconfig';

@Module({
  imports: [CacheModule.register<RedisClientOptions>({
    store: RedisStore,
    ttl: 30 * 24 * 3600,
    socket: redisConfig
  })],
  providers: [KvstoreService],
  exports: [KvstoreService]
})
export class KvstoreModule implements OnApplicationShutdown {
  constructor(@Inject(CACHE_MANAGER) private readonly cacheManager: Cache) { }
  async onApplicationShutdown() {
    const store = (this.cacheManager.store as any);
    if (store.getClient) {
      await store.getClient().quit();
    }
    console.log('ss');
  }
}
