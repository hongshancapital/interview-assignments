import { Controller, Get, Query } from '@nestjs/common';
import { AppService } from './app.service';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) { }

  /**
   * Get the short url by the given origin url.
   * @param originURL - Origin URL.
   * @returns - Short URL
   */
  @Get('/getShortURL')
  async getShortURL(@Query('url') url) {
    const shortURL = await this.appService.saveShortAndOriginURL(url);
    return {
      code: 0,
      data: shortURL,
      msg: ''
    };
  }

  /**
   * Get the origin URL by the short URL.
   * @param shortURL - Short URL.
   * @returns Origin URL
   */
  @Get('/getOriginURL')
  async getOriginURL(@Query('url') url) {
    const originURL = await this.appService.getOriginURLByShortKey(url);
    return {
      code: 0,
      data: originURL,
      msg: ''
    };
  }
}
