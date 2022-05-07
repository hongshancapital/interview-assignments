// This file is created by egg-ts-helper@1.30.3
// Do not modify this file!!!!!!!!!

import 'egg';
import ExportUrlMap from '../../../app/model/url-map';

declare module 'egg' {
  interface IModel {
    UrlMap: ReturnType<typeof ExportUrlMap>;
  }
}
