// This file is created by egg-ts-helper@1.25.2
// Do not modify this file!!!!!!!!!

import 'egg';
import ExportShortUrl from '../../../app/model/shortUrl';
import ExportTest from '../../../app/model/test';

declare module 'sequelize' {
  interface Sequelize {
    ShortUrl: ReturnType<typeof ExportShortUrl>;
    Test: ReturnType<typeof ExportTest>;
  }
}
