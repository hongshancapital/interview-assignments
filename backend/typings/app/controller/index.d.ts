// This file is created by egg-ts-helper@1.25.2
// Do not modify this file!!!!!!!!!

import 'egg';
import ExportShortUrl from '../../../app/controller/shortUrl';

declare module 'egg' {
  interface IController {
    shortUrl: ExportShortUrl;
  }
}
