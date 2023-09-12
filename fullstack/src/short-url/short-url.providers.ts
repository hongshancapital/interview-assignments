import { DataSource } from 'typeorm';
import { ShortUrl } from './short-url.entity';

export const shortUrlProviders = [
  {
    provide: 'SHORTURL_REPOSITORY',
    useFactory: (dataSource: DataSource) => dataSource.getRepository(ShortUrl),
    inject: ['DATA_SOURCE'],
  },
];
