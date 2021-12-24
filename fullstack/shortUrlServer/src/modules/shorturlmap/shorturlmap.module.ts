import { Module } from '@nestjs/common';
import { ShortUrlMapService } from './shorturlmap.service';
import { HashService } from '../../services/hash.service'
import { RedisService } from '../../services/redis.service'
import { BloomFilterService } from '../../services/bloomFilter.service'
import { ShortUrlMapController } from './shorturlmap.controller';

import { TypeOrmModule } from '@nestjs/typeorm';
import { ShortUrlMap } from './shorturlmap.entity';

@Module({
    imports: [
        TypeOrmModule.forFeature([ShortUrlMap], 'primary'),
        TypeOrmModule.forFeature([ShortUrlMap], 'backup')
    ],
    // imports: [TypeOrmModule.forFeature([ShortUrlMap])],
    providers: [ShortUrlMapService, HashService, RedisService, BloomFilterService],
    controllers: [ShortUrlMapController],
})
export class ShortUrlMapModule { }