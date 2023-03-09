import { Injectable } from '@nestjs/common';

import { UrlRepository } from './url.repository';
import { Url } from '../db/url.entity';

import { createHash } from 'node:crypto';

@Injectable()
export class AppService {
  private readonly firstLetterBase = 'abcdefghijklmnopqrstuvwsyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
  private readonly firstLetterLen: number;
  private readonly pool = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
  private readonly poolLength: number;

  constructor(private readonly urlRepository: UrlRepository) {
    this.firstLetterLen = this.firstLetterBase.length;
    this.poolLength = this.pool.length;
  }

  getOrCreateCode(originalUrl: string): string {
    // first two letters of the code are generated from the first four bytes of the hash buffer,
    // (used by load balancing and table/database splitting of the long-short/short-long lookups in the future)

    const buffer = createHash('sha1').update(originalUrl).digest();
    let start = buffer.readUInt32LE();
    let prefix = this.firstLetterBase[start % this.firstLetterLen];
    start = Math.floor(start / this.firstLetterLen);
    prefix += this.pool[start % this.poolLength];
    const sha1Str = buffer.toString('hex');

    let item = this.urlRepository.findByOriginalUrl(originalUrl, prefix, sha1Str);
    if (item) {
      return item.code;
    }
    item = new Url();
    item.code = this.getUniqueCode(prefix);
    item.originalUrl = originalUrl;
    item.originalUrlHash = sha1Str;
    item = this.urlRepository.add(item);
    this.urlRepository.update(item);

    return item.code;
  }

  getOriginalUrl(code: string): string | undefined {
    const entity = this.urlRepository.findByCode(code);

    return entity?.originalUrl;
  }

  private getUniqueCode(prefix: string) {
    const length = 6;
    let code = prefix + this.getRandomCode(length);
    while (this.urlRepository.isCodeExist(code)) {
      code = prefix + this.getRandomCode(length);
    }

    return code;
  }

  private getRandomCode(length: number) {
    let targetLength = length;
    let randomStr = '';
    for (; targetLength--; ) {
      randomStr += this.pool[Math.floor(Math.random() * this.poolLength)];
    }

    return randomStr;
  }
}
