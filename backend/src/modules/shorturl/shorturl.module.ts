import { Module, forwardRef } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';

import { ShorturlEntity } from './shorturl.entity';
import { ShorturlService } from './shorturl.service';
import { ShorturlController } from './shorturl.controller';
import { ConfigModule } from '../config/config.module';

@Module({
  imports: [
    TypeOrmModule.forFeature([ShorturlEntity]),
    forwardRef(() => ConfigModule)
  ],
  providers: [ShorturlService],
  controllers: [ShorturlController],
  exports: [ShorturlService]
})
export class ShorturlModule { }
