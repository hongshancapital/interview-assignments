import { CacheService, StorageItem } from "./types";

export const DEFAULT_EXPIRED_MS =  1000 * 60 * 3;

export default class MemoryCache implements CacheService {
  private static _instance =  new MemoryCache();

  static getInstance(): MemoryCache {
    return this._instance;
  }

  private _cacheDic: Record<string, StorageItem> = {};
  
  getValue = (key: string): any => {
    const item: StorageItem = this._cacheDic[key];
    const now = new Date().getTime();
    if (item.expiredAt < now) {
      delete this._cacheDic[key];
      return undefined;
    }
    return item.value;
  }

  setValue = (key: string, value: any, expiredSec?: number) => {
    const msToLive = expiredSec ? expiredSec * 1000 :  DEFAULT_EXPIRED_MS;
    const item: StorageItem = {
      value,
      expiredAt: new Date().getTime() +  msToLive,
    };
    this._cacheDic[key] = item;
  }
}

