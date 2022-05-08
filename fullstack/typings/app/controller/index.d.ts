// This file is created by egg-ts-helper@1.30.3
// Do not modify this file!!!!!!!!!

import 'egg';
import ExportUrlMap from '../../../app/controller/url-map';

declare module 'egg' {
  interface IController {
    urlMap: ExportUrlMap;
  }
}
