import { Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { ShortUrlRepository } from './entity/short-url.repository';
import { StatusCode } from './model/status_code';
import { PageInfo } from './entity/page-info';

const MAX_SHORT_URL_LENGTH = 8;
const DOMAIN = 'https://short-url.com/';

@Injectable()
export class ShortUrlService {
  constructor(
    @InjectRepository(ShortUrlRepository)
    private readonly shortUrlRepository: Repository<ShortUrlRepository>,
  ) { }

  /**
   * This function creates a shortUrl for the given longUrl. 
   * If the longUrl already exists, the existing shortUrl associated with it is returned. 
   * Otherwise, a new shortUrl is generated and associated with the given longUrl. 
   * The new shortUrl is then saved to the database and returned.
   * @param longUrl 
   * @returns 
   */
  async createShortUrl(longUrl: string): Promise<PageInfo> { // 省略生成短域名的实现
    if (!longUrl || longUrl == undefined || longUrl.length === 0) {
      return new PageInfo(StatusCode.ParamsIsNull, '', '参数为空');
    }

    if (!this.isUrlValid(longUrl)) {
      return new PageInfo(StatusCode.ParamsInvalid, '', '参数不合法');
    }

    const existingUrl = await this.shortUrlRepository.findOne({ where: { longUrl: longUrl } });
    if (existingUrl) {
      return new PageInfo(StatusCode.Success, existingUrl.shortUrl, '成功');
    } else {
      // 如果不存在，则创建新的短链接
      let shortUrl = this.generateShortUrl();
      shortUrl = DOMAIN + shortUrl;

      // 确保新生成的短链接在数据库中没有重复
      while (await this.shortUrlRepository.findOne({ where: { shortUrl: shortUrl } })) {
        shortUrl = this.generateShortUrl();
      }

      // 保存到数据库
      const url = new ShortUrlRepository();
      url.longUrl = longUrl;
      url.shortUrl = shortUrl;
      await this.shortUrlRepository.save(url);

      return new PageInfo(StatusCode.Success, existingUrl.shortUrl, '成功');
    }
  }

  /**
   * Gets the longUrl of a shortUrl from the shortUrlRepository. 
   * If the shortUrl exists, it returns the longUrl. 
   * Otherwise, it returns an empty string.
   * @param shortUrl 
   * @returns 
   */
  async getLongUrl(shortUrl: string): Promise<PageInfo> {
    if (!shortUrl || shortUrl == undefined || shortUrl.length === 0) {
      return new PageInfo(StatusCode.ParamsIsNull, '', '参数为空');
    }

    if (!this.isUrlValid(shortUrl)) {
      return new PageInfo(StatusCode.ParamsInvalid, '', '参数不合法');
    }

    const existingUrl = await this.shortUrlRepository.findOne({ where: { shortUrl: shortUrl } });
    if (existingUrl) {
      return new PageInfo(StatusCode.Success, existingUrl.longUrl, '成功');
    }
    return new PageInfo(StatusCode.NotFound, '', 'Not found');
  }

  /**
   * This function generates a short url of length MAX_SHORT_URL_LENGTH 
   * by randomly selecting characters from the given list of characters.
   * @returns 
   */
  private generateShortUrl(): string {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let shortUrl = '';
    for (let i = 0; i < MAX_SHORT_URL_LENGTH; i++) {
      shortUrl += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return shortUrl;
  }

  private isUrlValid(url) {
    const pattern = /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w\.\-]*)*\/?$/;
    return pattern.test(url);
  }
}
