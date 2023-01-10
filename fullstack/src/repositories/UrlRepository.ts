import { BASE_URL } from '@base/env';
import IUrlRepository from './IUrlRepository';
import UrlSchema from '../schemas/UrlSchema';

class UrlRepository implements IUrlRepository {
  public async createShortUrl(originalUrl: string): Promise<string> {
    const url = await UrlSchema.findOne({ originalUrl });

    if (url) return BASE_URL + url.shortPath;

    const shortPath = Math.random().toString(36).substring(2, 8);
    await UrlSchema.create({ originalUrl, shortPath });

    return BASE_URL + shortPath;
  }

  public async findByShortPath(shortPath: string): Promise<string | undefined> {
    const findUrl = await UrlSchema.findOne({ shortPath });
    const originalUrl = findUrl?.originalUrl;
    return originalUrl;
  }
}

export default UrlRepository;
