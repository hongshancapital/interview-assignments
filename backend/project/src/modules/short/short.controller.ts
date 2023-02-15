import { Body, Param, Controller, Get, Post } from '@nestjs/common';
import { GetReqDto } from './dto/get.req.dto';
import { UploadReqDto } from './dto/upload.req.dto';
import { ShortService } from './short.service';

@Controller('/short')
export class ShortController {
  constructor(private shortService: ShortService) {}

  @Post('/upload')
  async upload(@Body() dto: UploadReqDto) {
    let url = await this.shortService.upload(dto.url);
    return { url };
  }

  @Get('/:code')
  async get(@Param() dto: GetReqDto) {
    let url = await this.shortService.getUrlByCode(dto.code);
    return { url };
  }
}
