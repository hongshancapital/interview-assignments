import LRUCache from 'lru-cache';
import config from 'config';

/**
 * 缓存对象
 */
export const cache = new LRUCache<string, string>(config.get('cacheOptions'));
