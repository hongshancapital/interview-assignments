import LRU from 'lru-cache';
import { ReqInfo } from '../constant';

const cache = new LRU({
  max: 1,
  maxAge: 1000 * 30,
  updateAgeOnGet: true,
  maxSize: 10,
  length(value: ReqInfo, key: string) {
    const strLen = key.length + JSON.stringify(value).length;

    // 按最糟情况换算成M
    return Math.ceil((strLen * 2) / (1024 * 1024));
  },
});

const cacheGet = (id: string) => {
  return cache.get(id);
};

const cacheSet = (id: string, info: ReqInfo ) => {
  cache.set(id, info);
};

export { cacheGet, cacheSet };
