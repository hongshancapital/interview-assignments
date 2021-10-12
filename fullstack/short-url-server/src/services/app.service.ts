import { Injectable } from '@nestjs/common';
import { getRepository, getManager } from 'typeorm';
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
    
    const shortUrl = await getManager().transaction(async transactionalEntityManager => {
      const count = await repository.count();
      const cshortUrl = `${config.SERVER_URL}/${string10to62(count)}`;
      const url = new Url(cshortUrl, longUrl);
      url.longUrl = longUrl;
      await repository.save(url);
      return cshortUrl;
    });
    
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
