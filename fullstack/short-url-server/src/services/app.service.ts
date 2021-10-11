import { Injectable } from '@nestjs/common';
import { getRepository } from 'typeorm';
import { Url } from '../entities/url';
import { string10to62 } from '../common/stringUtil';
import config from '../common/config';

@Injectable()
export class AppService {
  async insertUrl(longUrl: string): Promise<string> {
    const repository = getRepository(Url);
    const existedUrl = await repository.findOne({ longUrl });
    if (existedUrl) {
      return existedUrl.shortUrl;
    }
    const count = await repository.count();
    const shortUrl = `${config.SERVER_URL}/${string10to62(count)}`;
    const url = new Url(shortUrl, longUrl);
    url.longUrl = longUrl;
    await repository.save(url);
    return shortUrl;
  }

  async findUrl(shortUrl: string): Promise<string> {
    const repository = getRepository(Url);
    const url = await repository.find({ shortUrl });
    if (url && url.length > 0) {
      return url[0].longUrl;
    }
    return null;
  }
}
