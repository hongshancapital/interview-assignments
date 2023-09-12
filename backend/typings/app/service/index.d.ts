// This file is created by egg-ts-helper@1.25.2
// Do not modify this file!!!!!!!!!

import 'egg';
import ExportShortId from '../../../app/service/shortId';
import ExportShortUrl from '../../../app/service/shortUrl';
import ExportShortUrlBloom from '../../../app/service/shortUrlBloom';
import ExportShortUrlCache from '../../../app/service/shortUrlCache';
import ExportTest from '../../../app/service/test';

declare module 'egg' {
  interface IService {
    shortId: ExportShortId;
    shortUrl: ExportShortUrl;
    shortUrlBloom: ExportShortUrlBloom;
    shortUrlCache: ExportShortUrlCache;
    test: ExportTest;
  }
}
