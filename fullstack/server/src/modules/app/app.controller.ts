import { Get, Controller, Param, Res, HttpStatus } from '@nestjs/common';
import { ApiBearerAuth, ApiOperation, ApiTags } from '@nestjs/swagger';
import { InjectRedis, Redis } from '@nestjs-modules/ioredis';
import { ShorturlService } from '../shorturl/shorturl.service';
import { string62to10 } from '../../common/uitls';

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
    const id = String(string62to10(url))
    const result = await this.redis.get(id);
    if (!!result) {
      res.status(HttpStatus.MOVED_PERMANENTLY).redirect(result);
    } else {
      const result2 = await this.shorturlService.findOneById(id);
      if (result2 && result2.url) {
        this.redis.set(id, result2.url)
        res.status(HttpStatus.MOVED_PERMANENTLY).redirect(result2.url);
      } else {
        res.status(HttpStatus.NOT_FOUND).end('404 Not Found');
      }
    }
  }
}
