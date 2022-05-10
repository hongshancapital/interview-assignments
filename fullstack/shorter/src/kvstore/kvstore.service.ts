import { CACHE_MANAGER, Inject, Injectable } from '@nestjs/common';
import { Cache } from 'cache-manager';

@Injectable()
export class KvstoreService {
  constructor(@Inject(CACHE_MANAGER) private readonly cacheManager: Cache) { }

  /**
   * Set key-value to store.
   * @param key 
   * @param value 
   */
  async setKV(key: string, value: string) {
    // Set key-value in redis cache
    await this.cacheManager.set(key, value);
    // Set key-value in persistent db
  }

  async getValue(key: string) {
    // Get key from redis cache
    const url = await this.cacheManager.get<string>(key);
    // If url is empty then get the key from persistent db 
    return url;
  }


}
