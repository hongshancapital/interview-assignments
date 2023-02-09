import { Inject, Controller, Get, Query } from '@midwayjs/decorator';
import { Context } from '@midwayjs/koa';
import { UrlConvertService } from '../service/url.service';

@Controller('/api') //设置前缀
export class APIController {
  @Inject()
  ctx: Context;

  @Inject()
  urlConvertService: UrlConvertService;


  /**
   * 查找所有
   * @returns 
   */
  @Get('/makeShotUrl')
  async makeShotUrl(@Query('long_url') long_url: string) {
    try {

    const urlInfo = await this.urlConvertService.makeShotUrl(long_url);
    return { success: true, message: 'OK', data: urlInfo };
          
  } catch (error) {
    return { success: false, message: error.message, };
  }
  }

  /**
   * 添加
   * @param user 
   * @returns 
   */
  @Get('/getLongUrl')
  async getLongUrl(@Query('short_url') short_url: string) {
    try {
      if (!short_url) {
        throw new Error('short_url 不能为空');
      }
      const info = await this.urlConvertService.queryByShortUrl(short_url);
      return { success: true, message: 'OK', data: info&& info.id ? info.long_url:'' };
    } catch (error) {
      return { success: false, message: error.message, };
    }
  }
}
