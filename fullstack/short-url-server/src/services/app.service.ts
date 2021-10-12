import { Injectable } from '@nestjs/common';
import { getRepository, getManager } from 'typeorm';
import { Url } from '../entities/url';
import config from '../common/config';
import { nanoid } from 'nanoid';

@Injectable()
export class AppService {
  async insertUrl(longUrl: string): Promise<string> {
    const repository = getRepository(Url);
    const existedUrl = await repository.findOne({ longUrl });
    if (existedUrl) {
      return existedUrl.shortUrl;
    }

    const shortUrl = `${config.SERVER_URL}/${nanoid(8)}`;
    const url = new Url(shortUrl, longUrl);
    url.longUrl = longUrl;
    await repository.save(url);
    return shortUrl;
  }

  async findUrl(shortUrl: string): Promise<string> {
    const repository = getRepository(Url);
    const url = await repository.findOne({ shortUrl });
    if (url) {
      return url.longUrl;
    }
    return null;
  }
}
