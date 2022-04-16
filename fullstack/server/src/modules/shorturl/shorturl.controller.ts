import {
  Controller,
  Get,
  Post,
  Query,
  Body,
  HttpStatus,
} from '@nestjs/common';
import { InjectRedis, Redis } from '@nestjs-modules/ioredis';
import {
  ApiBearerAuth,
  ApiOperation,
  ApiTags,
} from '@nestjs/swagger';

import { ShorturlService } from './shorturl.service';
import { ShorturlEntity } from './shorturl.entity';
import { string10to62, string62to10, uuid } from '../../common/uitls';
import { IVerifyLongUrl, IVerifyShortUrl } from './shorturl.dto';
import { ConfigService } from '../config/config.service';

@ApiBearerAuth()
@ApiTags('Short URL')
@Controller('api')
export class ShorturlController {
  constructor(
    @InjectRedis() private readonly redis: Redis,
    private readonly shorturlService: ShorturlService,
    private readonly configService: ConfigService
  ) { }

  @Get('shorturl/getlong')
  @ApiOperation({ summary: '获取长域名信息' })
  async getlong(@Query() query: IVerifyShortUrl) {
    const res = await this.shorturlService.findOneById(
      string62to10(query.s_url.split('/').pop())
    );
    
    return res ? {
      statusCode: HttpStatus.OK,
      message: '获取成功',
      data: res.url
    } : {
      statusCode: HttpStatus.NOT_FOUND,
      message: '链接不存在',
    }
  }

  @Get('shorturl/getshort')
  @ApiOperation({ summary: '获取短域名信息' })
  async getshort(@Query() query: IVerifyLongUrl) {
    const urlEngtity = new ShorturlEntity()
    urlEngtity.url = query.url
    let resultUrl = ''
    let res = await this.shorturlService.findOneOrFail(urlEngtity);
    
    if (!res || !res.id) {
      res = await this.create(urlEngtity)
    }
    
    resultUrl = String(string10to62(Number(res.id)));

    return {
      statusCode: HttpStatus.OK,
      message: '获取成功',
      data: `${this.configService.get('BASE_URL')}/${resultUrl}`
    }
  }

  async create(@Body() shorturl: ShorturlEntity) {
    const result = await this.shorturlService.save(shorturl);
    if (result && result.id) {
      try {
        this.redis.set(result.id, shorturl.url)
      } catch (e) {
        console.log(e)
      }
    }

    return result
  }
}

