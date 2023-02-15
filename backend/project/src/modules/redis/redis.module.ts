import { Module } from '@nestjs/common';
import { RedisService } from './redis.service';
import { RedisShortService } from './redis.short.service';

@Module({
  imports: [],
  controllers: [],
  providers: [RedisShortService, RedisService],
  exports: [RedisShortService],
})
export class RedisModule {}
