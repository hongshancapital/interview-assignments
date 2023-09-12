import { Module } from '@nestjs/common';

import { DatabaseModule } from '../database/database.module';
import { SharedModule } from '../shared/shared.module';

import { ShortUrlController } from './short-url.controller';
import { ShortUrlService } from './short-url.service';
import { shortUrlProviders } from './short-url.providers';

@Module({
  imports: [DatabaseModule, SharedModule],
  controllers: [ShortUrlController],
  providers: [...shortUrlProviders, ShortUrlService],
})
export class ShortUrlModule { }
