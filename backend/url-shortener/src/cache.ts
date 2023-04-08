import LRUCache from 'lru-cache';

export const cache = new LRUCache<string, string>({
    max: 100,
    // how long to live in ms
    ttl: 1000 * 60 * 60,
    // 获取时更新存活时间
    updateAgeOnGet: true,
});
