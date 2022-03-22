import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';

import { ShorturlEntity } from './shorturl.entity';
import { ShorturlService } from './shorturl.service';
import { ShorturlController } from './shorturl.controller';

@Module({
  imports: [
    TypeOrmModule.forFeature([ShorturlEntity])
  ],
  providers: [ShorturlService],
  controllers: [ShorturlController],
  exports: [ShorturlService]
})
export class ShorturlModule { }
