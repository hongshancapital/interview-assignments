import { Controller, Get, NotFoundException, Query } from '@nestjs/common'
// eslint-disable-next-line @typescript-eslint/consistent-type-imports
// import { ConfigService } from '@nestjs/config'
import { ApiOperation, ApiResponse, ApiTags } from '@nestjs/swagger'
import { nanoid } from 'nanoid'
// eslint-disable-next-line @typescript-eslint/consistent-type-imports
import { GetShortUrlDto, GetShortUrlVo, GetUrlDto, GetUrlVo } from './dto'
// eslint-disable-next-line @typescript-eslint/consistent-type-imports
import { UrlService } from './short-url.service'

@ApiTags('短链服务')
@Controller()
export class UrlController {
  constructor(
    // private readonly configService: ConfigService,
    private readonly urlService: UrlService
  ) {}

  /// 接收长链接返回短域名
  @ApiOperation({
    summary: '生成或获取短链'
  })
  @ApiResponse({
    type: GetShortUrlVo
  })
  @Get('url')
  async getOrCreateShortUrl(@Query() query: GetShortUrlDto) {
    const slug = nanoid(5).toLowerCase()
    const result = await this.urlService.findByUrl(query.url)
    if (result) {
      return result
    }
    const shortUrl = `https://hs.cn/${slug}`
    const item = await this.urlService.create(query.url, shortUrl)
    return item
  }

  /// 接收短链接返回长域名
  @ApiOperation({
    summary: '根据短链获取原始链接'
  })
  @ApiResponse({
    type: GetUrlVo
  })
  @Get('shorturl')
  async getUrl(@Query() query: GetUrlDto) {
    const result = await this.urlService.findByShortUrl(query.shortUrl)
    if (!result) {
      throw new NotFoundException('url 不存在')
    }
    return result
  }
}
