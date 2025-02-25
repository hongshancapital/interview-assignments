/*
 * simple store data in memory without outdate
 * we can use redis or other modules
 */

const cacheData = new Map<string, any>();

export function get(key: string) {
  return cacheData.get(key);
}

export function set(key: string, data: any) {
  cacheData.set(key, data);
  return data;
}

export function clear() {
  cacheData.clear();
}
