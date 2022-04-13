import { Controller, Get, Post, Req, Redirect } from '@nestjs/common';
import { AppService } from './app.service';
import { Request } from 'express';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get()
  getHello(): string {
    return this.appService.getHello();
  }

  @Get('/r/:surl')
  @Redirect('https://docs.nestjs.com', 302)
  async getLongUrl(@Req() request: Request) {
    const surl = request.params.surl;

    const longUrl = await this.appService.getLongUrl(surl);

    return {
      url: longUrl,
    };
  }

  @Post('/l2s')
  async LongToShort(@Req() request: Request): Promise<string> {
    const body = request.body;
    return await this.appService.LongToShort(body.longUrl);
  }
}
