import { Provide } from '@midwayjs/core';
import { InjectEntityModel } from '@midwayjs/typeorm';
import { UrlConvert } from '../entity/UrlConvert.entity';
import { Repository } from 'typeorm';

@Provide()
export class UrlConvertService {

  @InjectEntityModel(UrlConvert)
  urlConvertModel: Repository<UrlConvert>;


  /**
   * 随机生成指定长度的短域名
   * @param num 
   * @returns 
   */
  randomString(num: number) {
    var str = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    var result = '';
    for (var i = num; i > 0; --i)
      result += str[Math.floor(Math.random() * str.length)];
    return result;
  }

  /**
   * 生成短域名
   * @param long_url 
   * @returns 
   */
  async makeShotUrl(long_url: string) {
    let urlInfo = new UrlConvert();

    urlInfo.long_url = long_url;
    urlInfo.short_url = this.randomString(8);
    let result = await this.urlConvertModel.save(urlInfo)
    if (result && result.id) {
      return result.short_url
    }
    throw new Error("生产短域名失败")
  }

  /**
   * 查询长域名
   * @param short_url 
   * @returns 
   */
  async queryByShortUrl(short_url: string): Promise<UrlConvert> {
    return await this.urlConvertModel.findOne({ where: { short_url: short_url } })
  }

}
