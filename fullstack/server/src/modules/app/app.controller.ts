import { Get, Controller, Param, Res } from '@nestjs/common';
import { ApiBearerAuth, ApiOperation, ApiTags } from '@nestjs/swagger';
import { InjectRedis, Redis } from '@nestjs-modules/ioredis';
import { ShorturlService } from '../shorturl/shorturl.service';

@ApiBearerAuth()
@ApiTags('Short 页面')
@Controller()
export class AppController {
  constructor(
    @InjectRedis() private readonly redis: Redis,
    private readonly shorturlService: ShorturlService
  ) { }
  @ApiOperation({ summary: 'Short URL 重定向到长 URL' })
  @Get('/:url')
  async index(@Param('url') url: string, @Res() res) {
    const result = await this.redis.get(url);
    if (!!result) {
      res.status(301).redirect(result);
    } else {
      const result2 = await this.shorturlService.findOneBy({
        s_url: url
      });
      if (result2 && result2.url) {
        this.redis.set(url, result2.url)
        res.status(301).redirect(result2.url);
      } else {
        res.status(404).end('404 Not Found');
      }
    }
  }
}
