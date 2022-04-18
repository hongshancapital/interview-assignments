import { Controller, Get, Post, Req, Redirect } from '@nestjs/common';
import { AppService } from './app.service';
import { Request } from 'express';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get('/r/:surl')
  @Redirect('http://www.baidu.com', 302)
  async getLongUrl(@Req() request: Request ) {
    const surl = request.params.surl;
    const longUrl = await this.appService.getLongUrl(surl);

    return {
      url: longUrl,
      statusCode: 302
    };
  }

  @Post('/l2s')
  async LongToShort(@Req() request: Request): Promise<string> {
    const body = request.body;
    return await this.appService.LongToShort(body.longUrl);
  }
}
