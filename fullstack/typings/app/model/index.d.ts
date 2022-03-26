// This file is created by egg-ts-helper@1.30.2
// Do not modify this file!!!!!!!!!

import 'egg';
import ExportShortUrl = require('../../../app/model/shortUrl');

declare module 'egg' {
  interface IModel {
    ShortUrl: ReturnType<typeof ExportShortUrl>;
  }
}
