import { Module } from '@nestjs/common';
import { CacheShortService } from './cache.short.service';

@Module({
  imports: [],
  controllers: [],
  providers: [CacheShortService],
  exports: [CacheShortService],
})
export class CacheModule {}
