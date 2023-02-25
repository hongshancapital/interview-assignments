export interface CacheService {
  getValue: (key:string) => any;
  setValue: (key:string, value: any, expiredSec?: number) => void;
}

export type StorageItem = {
  value: any;
  expiredAt: number;
}