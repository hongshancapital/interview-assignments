import { Controller, Get, HttpException, HttpStatus, Param, Query, Response } from '@nestjs/common';
import { ApiOperation, ApiTags } from '@nestjs/swagger';
import { AppService } from './app.service';
import { isValideUrl, EncodeUrl } from './utils/mapper';

@ApiTags('UrlMapping')
@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @ApiOperation({
    summary: '获取短域名',
    description: '通过长域名生成短域名',
  })
  @Get('/short_url')
  getShortUrl(@Query('full_url') full_url: string) {
    full_url=full_url.trim();
    if (full_url.length < 8 || full_url.length > 255 || !isValideUrl(full_url)) {
      throw new HttpException('非法参数!', HttpStatus.BAD_REQUEST);
    }
    return this.appService.getShortUrl(full_url);
  }

  @ApiOperation({
    summary: '获取短域名对应的长域名',
    description: '通过长域名生成短域名',
  })
  @Get('/full_url')
  getFullUrl(@Query('short_url') short_url: string) {
    short_url=short_url.trim();
    if (short_url.length != 8 ) {
      throw new HttpException('非法参数!', HttpStatus.BAD_REQUEST);
    }
    return this.appService.getFullUrl(short_url);
  }
}
