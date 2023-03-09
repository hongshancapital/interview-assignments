import { BadRequestException, Controller, Get, Query } from '@nestjs/common';
import { ApiResponse } from '@nestjs/swagger';

import { GetOriginalUrlInput, GetShortUrlInput } from './input';
import { GetOriginalUrlOutput, GetShortUrlOutput } from './output';
import { AppService } from '../service/app.service';
import { ConfigService } from '../service/config.service';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService, private readonly config: ConfigService) {}

  @Get('getShortUrl')
  @ApiResponse({ type: GetShortUrlOutput })
  getShortUrl(@Query() input: GetShortUrlInput): GetShortUrlOutput {
    return { shortUrl: `${this.config.urlPrefix}${this.appService.getOrCreateCode(input.originalUrl)}` };
  }

  @Get('getOriginalUrl')
  @ApiResponse({ type: GetOriginalUrlOutput })
  getOriginalUrl(@Query() input: GetOriginalUrlInput): GetOriginalUrlOutput {
    const code = input.shortUrl.replace(this.config.urlPrefix, '');

    const item = this.appService.getOriginalUrl(code);
    if (!item) {
      throw new BadRequestException(`Short url "${input.shortUrl}" not exist.`);
    }

    return { originalUrl: item };
  }
}
