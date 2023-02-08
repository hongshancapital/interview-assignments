import { Controller, Get, Post, Body, Param } from '@nestjs/common';
import { nanoid } from 'nanoid'
import { ShortService } from './short.service';
import { CreateShortDto } from './dto/create-short.dto';
import { SearchShortDto } from './dto/search-short.dto';

@Controller('short')
export class ShortController {
  constructor(private readonly shortService: ShortService) {}

  @Post("create")
  async create(@Body() createShortDto: CreateShortDto) {
    const res = await this.shortService.findByUrl(createShortDto.url)
    if (res) {
      return res
    }
    const shortKey = nanoid(8)
    return this.shortService.create(createShortDto.url, shortKey);
  }

  @Get("all")
  findAll() {
    return this.shortService.findAll();
  }

  @Get("search/:shortKey")
  async findByShortKey(@Param() searchShortDto: SearchShortDto) {
    const res = await this.shortService.findByShortKey(searchShortDto.shortKey);
    return res || {}
  }
}
