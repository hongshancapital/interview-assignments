import LRU from 'lru-cache';

const lruCache = new LRU<string, string>({
  max: 20000,
  // 存留5天
  ttl: 5 * 24 * 3600 * 1000,
})

export default lruCache;