import { Injectable } from '@nestjs/common';
import { LRU } from '../../common/lru';
import { config } from '../../common/config';
/**
 * 短码本地缓存
 */
@Injectable()
export class CacheShortService {
  private lru: LRU<string, string>;

  constructor() {
    this.lru = new LRU<string, string>(config.short.cache.lru.capacity);
  }

  get(key: string): string {
    return this.lru.get(key);
  }

  put(key: string, value: string): void {
    this.lru.put(key, value);
  }
}
