import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

import { UrlMapping } from './entity/url_mapping.entity';

import { EncodeUrl } from './utils/mapper';

@Injectable()
export class AppService {
  constructor(
    @InjectRepository(UrlMapping)
    private urlMappingRepo: Repository<UrlMapping>,
  ) {}

  /**
   * 获取短域名
   * @param full_url 需要转换的长域名
   * @returns 短域名编码
   */
  async getShortUrl(full_url: string) {
    // 判断是否已有存在，如果存在，直接返回相应short_url
    const existFull = await this.urlMappingRepo.findOneBy({
      full_url: full_url,
    });
    if (existFull) return existFull.short_url;

    let tmp = EncodeUrl(full_url);
    let i=0;
    // check数据库中short_url是否存在重复
    while(i < 5) {
      let existShort = await this.urlMappingRepo.findOneBy({
        short_url: tmp.short_url,
      });
      if (existShort) {
        tmp = EncodeUrl(full_url);
        continue;
      } else {
        await this.urlMappingRepo.save({
          short_url: tmp.short_url,
          full_url: tmp.url,
          salt: tmp['salt'],
        });
        break;
      }
    }
    if (i == 5) {
      throw new HttpException('无法生成对应短链记录，请稍后重试!', HttpStatus.BAD_REQUEST);      
    }
    return tmp.short_url;
  }

  /**
   * 获取短域名对应的长域名
   * @param short_url 短域名
   * @returns 长域名
   */
  async getFullUrl(short_url: string) {
    // 从数据库中拿到code对应的url，没有则返回空
    const row = await this.urlMappingRepo.findOne({
      where: { short_url },
      select: ['full_url'],
    });
    return row?.full_url;
  }
}
