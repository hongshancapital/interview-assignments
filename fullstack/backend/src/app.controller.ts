import {
  BadRequestException,
  Body,
  Controller,
  Get,
  Param,
  Post,
  Res,
  UseFilters,
} from '@nestjs/common';
import { AppService } from './app.service';
import * as validURL from 'valid-url';
import { DatabaseExceptionFilter } from './database-error.filter';
import { SERVICE_URL } from './configs';

@Controller()
@UseFilters(new DatabaseExceptionFilter())
export class AppController {
  constructor(private readonly appService: AppService) {}

  @Get('full/:slug')
  getFullURL(@Param('slug') slug) {
    return this.appService.shortToFull(slug);
  }

  @Post()
  async getShortenedURL(@Body() { url }: { url: string }) {
    if (!validURL.isWebUri(url)) {
      throw new BadRequestException();
    }
    const record = await this.appService.createRecord(url);
    return `${SERVICE_URL}/${record.slug}`;
  }

  @Get(':slug')
  async redirect(@Param('slug') slug, @Res() res) {
    const url = await this.appService.shortToFull(slug);
    res.redirect(url);
  }
}
