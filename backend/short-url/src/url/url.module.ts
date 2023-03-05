import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { UrlController } from './url.controller';
import { UrlService } from './url.service';
import { TypeOrmModule } from '@nestjs/typeorm';
import { UrlEntity } from './url.entity';

@Module({
  imports: [
    ConfigModule,
    TypeOrmModule.forFeature([UrlEntity]),
  ],
  controllers: [UrlController],
  providers: [UrlService],
  exports: [UrlService]
})
export class UrlModule { }
