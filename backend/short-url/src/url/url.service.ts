import { Injectable } from '@nestjs/common';
import { Repository } from 'typeorm';
import { UrlEntity } from './url.entity';
import { InjectRepository } from '@nestjs/typeorm';
import { ConfigService } from '@nestjs/config';
import { OriginUrl, ShortUrl } from '../@types/url';

@Injectable()
export class UrlService {
  private readonly shortUrlPrefix;
  private readonly base: string = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';

  constructor(
    @InjectRepository(UrlEntity)
    private readonly urlRepository: Repository<UrlEntity>,
    private readonly configService: ConfigService,
  ) {
    this.shortUrlPrefix = configService.get("SHORT_URL_PREFIX") || 'https://short.url'
  }

  async addUrl(originUrl: string): Promise<ShortUrl> {
    let entity = await this.urlRepository.findOneBy({ originUrl });
    if (entity) {
      return { shortUrl: entity.shortUrl };
    }
    entity = await this.urlRepository.save({ originUrl });

    const shortName = this.generateShortNameById(entity.id);
    const shortUrl = `${this.shortUrlPrefix}/${shortName}`;
    await this.urlRepository.update({ id: entity.id }, { shortUrl })

    return { shortUrl };
  }

  async getOriginUrl(shortUrl: string): Promise<OriginUrl> {
    const entity = await this.urlRepository.findOneBy({ shortUrl });
    return { originUrl: entity.originUrl };
  }

  generateShortNameById(id: number) {
    const arr = [];
    while (id > 0) {
      arr.push(this.base[id % this.base.length]);
      id = Math.floor(id / this.base.length);
    }
    return arr.reverse().join('');
  }

}
