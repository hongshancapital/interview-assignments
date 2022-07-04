/* eslint-disable prettier/prettier */
import { Module } from '@nestjs/common';
import { ShortLinkModule } from './short-link/short-link.module';

@Module({
  imports: [ShortLinkModule],
})
export class AppModule {}
