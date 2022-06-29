import { Module } from '@nestjs/common'
import { UrlController } from './short-url.controller'
import { UrlService } from './short-url.service'

@Module({
  imports: [],
  controllers: [UrlController],
  providers: [UrlService],
  exports: [UrlService]
})
export class UrlModule {}
