import { Get, Controller, Param, Res, HttpStatus, Header } from '@nestjs/common';
import { ApiOperation, ApiTags } from '@nestjs/swagger';
import { InjectRedis, Redis } from '@nestjs-modules/ioredis';
import { Response } from 'express';
import { ShorturlService } from '../shorturl/shorturl.service';
import { string62to10 } from '../../common/uitls';

@ApiTags('短域名页面链接')
@Controller()
export class AppController {
  constructor(
    @InjectRedis() private readonly redis: Redis,
    private readonly shorturlService: ShorturlService
  ) { }

  @ApiOperation({ summary: 'Short URL redirect to long URL' })
  @Get('/:url')
  async index(@Param('url') url: string, @Res() res: Response) {
    const id = String(string62to10(url))
    const result = await this.redis.get(id);

    if (!!result) {
      res.redirect(HttpStatus.FOUND, result);
    } else {
      const result2 = await this.shorturlService.findOneById(id);

      if (result2 && result2.url) {
        this.redis.set(id, result2.url)
        res.redirect(HttpStatus.FOUND, result2.url);
      } else {
        res.status(HttpStatus.NOT_FOUND).send('404 Not Found');
      }
    }
  }
}
