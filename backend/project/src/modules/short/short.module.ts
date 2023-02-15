import { Module } from '@nestjs/common';
import { ShortService } from './short.service';
import { ShortController } from './short.controller';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ShortEntity } from '../../entities/short.entity';
import { CacheModule } from '../cache/cache.module';
import { RedisModule } from '../redis/redis.module';

@Module({
  imports: [
    // 加载实体
    TypeOrmModule.forFeature([ShortEntity]),
    CacheModule,
    RedisModule,
  ],
  exports: [],
  providers: [ShortService],
  controllers: [ShortController],
})
export class ShortModule {}
