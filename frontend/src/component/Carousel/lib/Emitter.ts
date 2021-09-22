
import { mixin } from '../../../util';

export type ListenerFunction<
  T extends { [key: string]: unknown[] },
  K extends keyof T
> = (...params: T[K]) => void | Promise<void>;

export interface IListenerCacheItem<
  T extends { [key: string]: unknown[] },
  K extends keyof T
> {
  waiting?: T[K];
  fn: ListenerFunction<T, K>;
}

export type ListenerCache<T extends { [key: string]: unknown[] }> = {
  [K in keyof T]?: Array<IListenerCacheItem<T, K>>;
};

const EVENT_CACHE_KEY = Symbol('event_cache_key');

// 这里因为继承的原因，只能用any
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export class Emitter<T extends { [key: string]: any }> {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  public static mixin(Fn: { prototype: unknown }): void {
    mixin(Fn, Emitter);
  }

  private [EVENT_CACHE_KEY]?: ListenerCache<T>;

  // 添加监听函数
  public addListener<K extends keyof T>(
    type: K,
    listener: ListenerFunction<T, K>,
  ): void {
    const map: ListenerCache<T> = (this[EVENT_CACHE_KEY] =
      this[EVENT_CACHE_KEY] || {});

    let listeners = map[type];

    if (listeners == null) {
      listeners = [];
      map[type] = listeners;
    }

    listeners.push({
      fn: listener,
    });
  }

  // 删除监听函数，不传递listener表示删除当前事件的全部监听函数，不传递type清空全部
  public removeListener<K extends keyof T>(
    type?: K,
    listener?: ListenerFunction<T, K>,
  ): void {
    const map = this[EVENT_CACHE_KEY];

    if (type == null || map == null) {
      this[EVENT_CACHE_KEY] = {};
      return;
    }

    const listeners = map[type];

    if (listeners == null) {
      return;
    }

    if (listener == null) {
      delete map[type];
      return;
    }

    const index = listeners.findIndex((item) => item.fn === listener);

    if (index >= 0) {
      listeners.splice(index, 1);
    }
  }

  // 删除所有监听函数，删除当前事件的全部监听函数，不传递type清空全部
  public removeAllListeners<K extends keyof T>(type?: K): void {
    this.removeListener(type);
  }

  // 触发事件
  public emit<K extends keyof T>(type: K, ...params: T[K]): void {
    const map: ListenerCache<T> = (this[EVENT_CACHE_KEY] =
      this[EVENT_CACHE_KEY] || {});
    const listeners: undefined | Array<IListenerCacheItem<T, K>> = map[type];

    if (listeners == null) {
      return;
    }

    for (const listener of listeners) {
      listener.fn.apply(this, params);
    }
  }


  // 获取所有监听函数
  public listeners<K extends keyof T>(type: K): Array<ListenerFunction<T, K>> {
    const map: ListenerCache<T> = (this[EVENT_CACHE_KEY] =
      this[EVENT_CACHE_KEY] || {});
    const listeners: undefined | Array<IListenerCacheItem<T, K>> = map[type];

    if (listeners == null) {
      return [];
    }

    return listeners.map((item) => item.fn);
  }

  public listenerCount<K extends keyof T>(type: K): number {
    const map: ListenerCache<T> = (this[EVENT_CACHE_KEY] =
      this[EVENT_CACHE_KEY] || {});
    const listeners = map[type];

    if (listeners == null) {
      return 0;
    }

    return listeners.length;
  }
}
