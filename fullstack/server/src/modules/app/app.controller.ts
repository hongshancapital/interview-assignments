import { Get, Controller, Param, Res } from '@nestjs/common';
import { ApiBearerAuth, ApiOperation, ApiTags } from '@nestjs/swagger';
import { InjectRedis, Redis } from '@nestjs-modules/ioredis';

@ApiBearerAuth()
@ApiTags('Short 页面')
@Controller()
export class AppController {
  constructor(
    @InjectRedis() private readonly redis: Redis
  ) { }
  @ApiOperation({ summary: 'Short URL 重定向到长 URL' })
  @Get('/:url')
  async index(@Param('url') url: string, @Res() res) {
    const result = await this.redis.get(url);
    if (!!result) {
      res.status(302).redirect(result);
      return
    } else {
      res.status(404).end('404 Not Found');
      return
    }
  }
}
