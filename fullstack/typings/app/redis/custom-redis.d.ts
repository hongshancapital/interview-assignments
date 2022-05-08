// This file is created by egg-ts-helper@1.30.3
// Do not modify this file!!!!!!!!!

import 'egg';
type AnyClass = new (...args: any[]) => any;
type AnyFunc<T = any> = (...args: any[]) => T;
type CanExportFunc = AnyFunc<Promise<any>> | AnyFunc<IterableIterator<any>>;
type AutoInstanceType<T, U = T extends CanExportFunc ? T : T extends AnyFunc ? ReturnType<T> : T> = U extends AnyClass ? InstanceType<U> : U;
import ExportBloomFilter from '../../../app/redis/bloom-filter';
import ExportUrlMap from '../../../app/redis/url-map';

declare module 'egg' {
  interface Application {
    redis: T_custom_redis;
  }

  interface T_custom_redis {
    bloomFilter: AutoInstanceType<typeof ExportBloomFilter>;
    urlMap: AutoInstanceType<typeof ExportUrlMap>;
  }
}
