// This file is created by egg-ts-helper@1.30.3
// Do not modify this file!!!!!!!!!

import 'egg';
type AnyClass = new (...args: any[]) => any;
type AnyFunc<T = any> = (...args: any[]) => T;
type CanExportFunc = AnyFunc<Promise<any>> | AnyFunc<IterableIterator<any>>;
type AutoInstanceType<T, U = T extends CanExportFunc ? T : T extends AnyFunc ? ReturnType<T> : T> = U extends AnyClass ? InstanceType<U> : U;
import ExportBloomFilter from '../../../app/service/bloom-filter';
import ExportCache from '../../../app/service/cache';
import ExportUrlMap from '../../../app/service/url-map';

declare module 'egg' {
  interface IService {
    bloomFilter: AutoInstanceType<typeof ExportBloomFilter>;
    cache: AutoInstanceType<typeof ExportCache>;
    urlMap: AutoInstanceType<typeof ExportUrlMap>;
  }
}
