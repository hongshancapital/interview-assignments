import { Body, Controller, Get, Param, Post } from '@nestjs/common';

import { ShortUrlService } from './short-url.service';
import { GetShortUrlParams, LongUrlDTO } from './short-url.dto';

@Controller()
export class ShortUrlController {
  constructor(private readonly shortUrlService: ShortUrlService) { }

  @Post()
  async toShort(@Body() body: LongUrlDTO): Promise<string> {
    return this.shortUrlService.getCode(body.url);
  }

  @Get(':code')
  async getLong(@Param() params: GetShortUrlParams): Promise<string> {
    return this.shortUrlService.getUrl(params.code);
  }
}
