import { Body, Controller, Post } from '@nestjs/common';
import { ApiOperation, ApiTags } from '@nestjs/swagger';
import { OriginUrl, ShortUrl } from '../@types/url';
import { UrlService } from './url.service';

@ApiTags('url')
@Controller('url')
export class UrlController {

  constructor(
    private readonly urlService: UrlService,
  ) { }

  @Post('2short')
  @ApiOperation({ description: '原始Url-->短Url' })
  async getShortUrl(@Body() data: OriginUrl) {
    return await this.urlService.addUrl(data.originUrl)
  }

  @Post('2origin')
  @ApiOperation({ description: '短Url-->原始Url' })
  async getOriginUrl(@Body() data: ShortUrl) {
    return await this.urlService.getOriginUrl(data.shortUrl)
  }
}
