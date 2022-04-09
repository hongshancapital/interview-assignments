import { Get, Controller, Param, Res, HttpStatus } from '@nestjs/common';
import { ApiBearerAuth, ApiOperation, ApiTags } from '@nestjs/swagger';
import { InjectRedis, Redis } from '@nestjs-modules/ioredis';
import { ShorturlService } from '../shorturl/shorturl.service';

@ApiBearerAuth()
@ApiTags('短域名页面链接')
@Controller()
export class AppController {
  constructor(
    @InjectRedis() private readonly redis: Redis,
    private readonly shorturlService: ShorturlService
  ) { }
  @ApiOperation({ summary: 'Short URL redirect to long URL' })
  @Get('/:url')
  async index(@Param('url') url: string, @Res() res) {
    const result = await this.redis.get(url);
    if (!!result) {
      res.status(HttpStatus.MOVED_PERMANENTLY).redirect(result);
    } else {
      const result2 = await this.shorturlService.findOne({
        s_url: url
      });
      if (result2 && result2.url) {
        this.redis.set(url, result2.url)
        res.status(HttpStatus.MOVED_PERMANENTLY).redirect(result2.url);
      } else {
        res.status(HttpStatus.NOT_FOUND).end('404 Not Found');
      }
    }
  }
}
