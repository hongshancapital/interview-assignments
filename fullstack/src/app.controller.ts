import { Controller, Get, Param, Query, Response } from '@nestjs/common';
import { ApiOperation, ApiTags } from '@nestjs/swagger';
import { AppService } from './app.service';

@ApiTags('Code')
@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @ApiOperation({
    summary: '获取短域名',
    description: '通过长域名生成短域名',
  })
  @Get('/short')
  short(@Query('url') url: string) {
    return this.appService.getShortCode(url);
  }

  @ApiOperation({
    summary: '获取短域名对应的长域名',
    description: '通过长域名生成短域名',
  })
  @Get('/long')
  long(@Query('code') code: string) {
    return this.appService.getLongUrl(code);
  }

  @ApiOperation({
    summary: '短域名跳转使用',
    description: '用生成的短域名跳转长域名，如果是无效短域名则跳到默认页面',
  })
  @Get('/r/:code')
  async redirect(@Param('code') code: string, @Response() res) {
    const longUrl = await this.appService.getLongUrl(code);
    // 没有找到code对应url，跳转到默认页面
    res.redirect(longUrl || 'http://wwww.baidu.com');
  }
}
