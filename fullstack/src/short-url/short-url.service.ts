import { Inject, Injectable } from '@nestjs/common';
import { Repository } from 'typeorm';

import { SharedService } from '../shared/shared.service';
import { ShortUrl } from './short-url.entity';

@Injectable()
export class ShortUrlService {
  constructor(
    @Inject('SHORTURL_REPOSITORY')
    private shortUrlRepository: Repository<ShortUrl>,
    private sharedService: SharedService,
  ) { }

  /**
   * 获取短链code
   * @param url 原始长链接
   */
  async getCode(url: string): Promise<string> {
    const hash = this.sharedService.crc32(url);
    // 先查询数据库中是否已存在当前url
    const exist = await this.shortUrlRepository.findOne({
      where: {
        hash: hash,
        url: url,
      },
    });
    if (exist && exist.code) {
      return exist.code;
    }

    // 先将url保存到数据库获得自增id
    const entity = new ShortUrl(url, hash, '');
    const result = await this.shortUrlRepository.save(entity);

    // 将id转换为code，并更新至数据库
    const code = this.numToCode(result.id);
    result.code = code;
    await this.shortUrlRepository.update(entity.id, entity);

    return entity.code;
  }

  /**
   * 获取短链code
   * @param code 短链code
   */
  async getUrl(code: string): Promise<string> {
    // 转换code为id
    const id = this.codeToNum(code);
    const shortUrlEntity = await this.shortUrlRepository.findOneById(id);

    if (!shortUrlEntity) {
      return '';
    }

    return shortUrlEntity.url;
  }

  /**
   * 自增id转短链code
   * @param num 自增id数字
   */
  numToCode(num: number): string {
    if (num <= 0) {
      throw new Error('num不能小于0！');
    }

    // 得到num的62位表示字符串
    const str = this.sharedService.to62Str(num);
    // 防止可猜测，最后一位加入随机数
    const random = this.sharedService.to62Str(Math.floor(Math.random() * 62));

    return str + random;
  }

  /**
   * 将短链转换为id
   * @param code 短链码
   */
  codeToNum(code: string): number {
    if (!code) {
      throw new Error('code参数不能为空！');
    }
    // 舍弃最后一位，并转换为十进制数字
    return this.sharedService.strToNum(code.slice(0, code.length - 1));
  }
}
