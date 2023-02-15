export class LRU<K, V> {
  private map = new Map();
  private capacity: number;
  constructor(capacity: number) {
    this.capacity = capacity;
  }

  /**
   * 获取值，没有获取到返回 null
   * @param key
   * @returns
   */
  get(key: K): V {
    if (this.map.has(key)) {
      // 放在最前面
      let val = this.map.get(key);
      this.map.delete(key);
      this.map.set(key, val);
      return val;
    }
    return null;
  }
  put(key: K, val: V) {
    // 如果有就删掉再赋值
    if (this.map.has(key)) this.map.delete(key);
    this.map.set(key, val);
    if (this.map.size > this.capacity) {
      // 删除老的
      this.map.delete(this.map.keys().next().value);
    }
  }
}
