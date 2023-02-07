// This file is created by egg-ts-helper@1.34.5
// Do not modify this file!!!!!!!!!
/* eslint-disable */

import 'egg';
type AnyClass = new (...args: any[]) => any;
type AnyFunc<T = any> = (...args: any[]) => T;
type CanExportFunc = AnyFunc<Promise<any>> | AnyFunc<IterableIterator<any>>;
type AutoInstanceType<T, U = T extends CanExportFunc ? T : T extends AnyFunc ? ReturnType<T> : T> = U extends AnyClass ? InstanceType<U> : U;
import ExportSiteService from '../../../app/service/siteService';

declare module 'egg' {
  interface IService {
    siteService: AutoInstanceType<typeof ExportSiteService>;
  }
}
