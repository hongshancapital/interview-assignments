import {
  Controller,
  Param,
  Get,
  Post,
  Req,
  Res,
  HttpStatus,
  Body,
  HttpException,
} from '@nestjs/common';
import { Request } from 'express';
import { AppService } from './app.service';

import {
  ApiParam,
  ApiQuery,
  ApiBody,
  ApiHeader,
  ApiHeaders,
} from '@nestjs/swagger';

import { Shorten } from './entity/shorten';
import { ShortenDto } from './dto/ShortenDto';

@Controller('api')
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Post('link')
  getShortened(@Body() shortenDto: ShortenDto) {
    let originalUrl;
    try {
      originalUrl = new URL(shortenDto.link);
    } catch (err) {
      throw new HttpException(
        {
          status: HttpStatus.BAD_REQUEST,
          error: 'invalid URL',
        },
        HttpStatus.BAD_REQUEST,
      );
    }

    return this.appService.shortenerLink(shortenDto);
  }

  @Get('link/:id')
  @ApiParam({
    name: 'id',
  })
  getLink(@Param() params, @Req() req) {
    // console.log('Retrieve shortened url: ', params.id);
    let shortenPromise = this.appService.retrieveShortened(params.id);
    this.appService.trackShortened(params.id, req);
    return shortenPromise;
  }
}
