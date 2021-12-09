import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';

import { Code } from './entity/code.entity';
import { EncodeStr } from './utils';

@Injectable()
export class AppService {
  constructor(@InjectRepository(Code) private codeRepo: Repository<Code>) {}

  /**
   * 获取短域名
   * @param url 需要转换的长域名
   * @returns 短域名编码
   */
  async getShortCode(url: string) {
    // 判断是否已有存在，如果存在，直接返回相应code
    const existRow = await this.codeRepo.findOne({ url });
    if (existRow && existRow) return existRow.code;

    // 取得id字段当前最大数用于通过算法获取code，此处在数据量大后可替换为用redis等方式来缓存最大数
    const maxRow = await this.codeRepo.query('select max(id) as num from code');
    const code = EncodeStr(Number(maxRow?.num ? maxRow.num + 1 : 1));

    // code和url都生成后，保存至数据库
    await this.codeRepo.save({
      code,
      url,
    });
    return code;
  }

  /**
   * 获取短域名对应的长域名
   * @param code 短域名
   * @returns 长域名
   */
  async getLongUrl(code: string) {
    // 从数据库中拿到code对应的url，没有则返回空
    const row = await this.codeRepo.findOne({
      where: { code },
      select: ['url'],
    });
    return row?.url;
  }

  /**
   * 删除短域名记录
   * @param code 短域名
   */
  async removeShortCode(code) {
    const { affected } = await this.codeRepo.delete({ code });
    return affected;
  }
}
