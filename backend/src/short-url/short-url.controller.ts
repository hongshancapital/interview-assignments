/* eslint-disable @typescript-eslint/no-var-requires */
import {
  Body,
  Controller,
  Get,
  Post,
  Query,
} from '@nestjs/common';
import { ShortUrlService } from './short-url.service';
import { PageInfo } from './entity/page-info';

@Controller('/api/short-url')
export class ShortUrlController {
  constructor(private readonly shortUrlService: ShortUrlService) { }

  /** 
    * Creates a short URL from a long URL 
    * 
    * @param longUrl Long URL from which to generate the short URL 
    * @returns An object containing the newly created short URL 
    */
  @Post('/create-short-url')
  async createShortUrl(@Body("longUrl") longUrl: string) {
    const shortUrl: PageInfo = await this.shortUrlService.createShortUrl(longUrl);
    return shortUrl;
  }

  /** 
  * This getLongUrl() function can be used to get the long URL associated with a given short URL.  
  * It takes a short URL as an argument and returns the long URL or a 'Not found' message if no long URL is associated with the given short URL. 
  */
  @Get('/get-long-url')
  async getLongUrl(@Query("shortUrl") shortUrl: string) {
    const longUrl: PageInfo = await this.shortUrlService.getLongUrl(shortUrl);
    return longUrl;
  }
}

