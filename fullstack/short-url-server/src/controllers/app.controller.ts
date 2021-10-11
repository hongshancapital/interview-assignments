import { Controller, Get, Post, Query, Body, Res, HttpStatus } from '@nestjs/common';
import { AppService } from '../services/app.service';
import errorCode from '../common/errorCode';
import { Response } from 'express';
import { validURL } from '../common/stringUtil';
import config from '../common/config';

@Controller('url')
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Post()
  async insertUrl(@Body('longUrl') longUrl: string, @Res() res: Response | any): Promise<Response | any> {
    if (!validURL(longUrl)) {
      return res.status(HttpStatus.OK).json({
        code: errorCode.URL_NOT_VALID,
        message: `invalid url: ${longUrl}`,
      });
    }

    const url = await this.appService.insertUrl(longUrl);

    if (url === `${config.SERVER_URL}/00`) {
      return res.status(HttpStatus.OK).json({
        code: errorCode.URL_Out_Of_Range,
        message: `out of range url: ${longUrl}`,
      });
    }

    return res.status(HttpStatus.OK).json({
      data: url,
      code: errorCode.SUCDESS,
    });
  }

  @Get()
  async findUrl(@Query('shortUrl') shortUrl: string, @Res() res: Response | any): Promise<Response | any> {
    if (!validURL(shortUrl)) {
      return res.status(HttpStatus.OK).json({
        code: errorCode.URL_NOT_VALID,
        message: `url not valid: ${shortUrl}`,
      });
    }

    const longUrl = await this.appService.findUrl(shortUrl);
    if (!longUrl) {
      return res.status(HttpStatus.OK).json({
        code: errorCode.URL_NOT_EXIST,
        message: `failed to find url: ${shortUrl}`,
      });
    }

    return res.status(HttpStatus.OK).json({
      data: longUrl,
      code: errorCode.SUCDESS,
    });
  }
}
