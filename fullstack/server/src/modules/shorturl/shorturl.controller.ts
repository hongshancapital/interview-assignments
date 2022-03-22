import {
  Controller,
  Res,
  Get,
  Post,
  Put,
  Delete,
  Query,
  Param,
  Body,
} from '@nestjs/common';
import { InjectRedis, Redis } from '@nestjs-modules/ioredis';
import {
  ApiBearerAuth,
  ApiOperation,
  ApiTags,
} from '@nestjs/swagger';

import { ShorturlService } from './shorturl.service';
import { ShorturlEntity } from './shorturl.entity';
import { IFindManyOptions } from '../../common/common.dto';
import { uuid } from '../../common/uitls';
import { IVerifyLongUrl, IVerifyShortUrl } from './shorturl.dto';

@ApiBearerAuth()
@ApiTags('Short URL')
@Controller('api')
export class ShorturlController {
  constructor(
    @InjectRedis() private readonly redis: Redis,
    private readonly shorturlService: ShorturlService
  ) { }

  @Get('shorturl/getlong')
  @ApiOperation({ summary: '获取长域名信息' })
  async findUrl(@Query() query: IVerifyShortUrl) {
    const res = await this.shorturlService.findOneBy({
      s_url: query.s_url
    });
    return res.url
  }

  @Get('shorturl/getshort')
  @ApiOperation({ summary: '获取短域名信息' })
  async findSUrl(@Query() query: IVerifyLongUrl) {
    const res = await this.shorturlService.findOneBy({
      url: query.url
    });

    if (res.s_url) {
      return res.s_url
    } else {
      const urlEngtity = new ShorturlEntity()
      urlEngtity.url = query.url
      const result = await this.create(urlEngtity)
      return result.s_url
    }
  }

  @Post('shorturls')
  @ApiOperation({ summary: '[Admin] 创建短域名信息' })
  async create(@Body() shorturl: ShorturlEntity) {
    const { url } = shorturl;
    setTimeout(() => {
      shorturl.s_url = uuid(8, 16)
      if (shorturl.status) {
        const rest = this.redis.set(shorturl.s_url, url)
      } else {
        this.redis.del(shorturl.s_url)
      }
      shorturl.s_url = uuid(8, 16)
      this.redis.set(shorturl.s_url, url)
    }, 0)
    return await this.shorturlService.create(shorturl);
  }

  @Delete('shorturls/:id')
  @ApiOperation({ summary: '[Admin] 删除短域名信息' })
  async delete(@Param('id') id: string) {
    const res = await this.shorturlService.delete(id);
    if (res) {
      setTimeout(async () => {
        const result = await this.shorturlService.findOneBy({
          id
        });
        this.redis.del(result.s_url)
      }, 0)
    }
    return res
  }

  @Put('shorturls')
  @ApiOperation({ summary: '[Admin] 修改短域名信息' })
  async patch(
    @Res() res,
    @Body() updateInput: ShorturlEntity,
  ) {
    const { url } = updateInput;
    setTimeout(() => {
      updateInput.s_url = uuid(8, 16)
      if (updateInput.status) {
        const rest = this.redis.set(updateInput.s_url, url)
      } else {
        this.redis.del(updateInput.s_url)
      }
    }, 0)
    return await this.shorturlService.save(updateInput);
  }

  @Get('shorturls/:id')
  @ApiOperation({ summary: '[Admin] 获取单个短域名信息' })
  async findOneById(@Param('id') id: string) {
    return await this.shorturlService.findOneBy({
      id
    });
  }

  @Get('shorturls')
  @ApiOperation({ summary: '[Admin] 获取全部短域名信息' })
  async findAll(@Query() query: IFindManyOptions<ShorturlEntity>) {
    return await this.shorturlService.findAndCount(query);
  }
}

