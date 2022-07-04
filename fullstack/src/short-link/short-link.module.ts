import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { LinkEntity } from '../entities/link.entity';
import { ShortLinkController } from './short-link.controller';
import { ShortLinkService } from './short-link.service';
import { IdModule } from '../id/id.module';
import { RedisModule } from '@liaoliaots/nestjs-redis';
import { getRedisConfig, getDbConfig } from '../config';

@Module({
  imports: [
    TypeOrmModule.forFeature([LinkEntity]),
    TypeOrmModule.forRoot(getDbConfig()),
    RedisModule.forRoot(getRedisConfig()),
    IdModule,
  ],
  controllers: [ShortLinkController],
  providers: [ShortLinkService],
})
export class ShortLinkModule {}
