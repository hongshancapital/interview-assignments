import {
  Inject,
  Controller,
  Get,
  Query,
  Post,
  Body,
} from '@midwayjs/decorator';
import { Context } from '@midwayjs/koa';
// import { LocalRedisService } from '../service/redis.service';
import { UrlService } from '../service/url.service';
// import { UrlModel } from '../entity/url';

@Controller('/api')
export class APIController {
  @Inject()
  ctx: Context;

  @Inject()
  urlService: UrlService;

  @Get('/getByShortUrl')
  async getByShortUrl(@Query('url') url) {
    const data = await this.urlService.getByShortUrl(url);
    return data;
  }

  @Post('/createByLongUrl')
  async createByLongUrl(@Body('url') url: string) {
    const data = await this.urlService.createByLongUrl(url);
    return data;
  }
}
