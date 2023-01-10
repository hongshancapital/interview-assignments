import { BASE_URL } from '@base/env';
import IUrlRepository from './IUrlRepository';
import UrlSchema from '../schemas/UrlSchema';
import BizError from '@error/BizError';

class UrlRepository implements IUrlRepository {

  static RETRY_TIMES = 3;

  public async createShortUrl(originalUrl: string): Promise<string> {
    const url = await UrlSchema.findOne({ originalUrl });

    if (url) return BASE_URL + url.shortPath;

    let shortPath = '';
    let tryTime = 0;

    // 防碰撞处理
    while(tryTime < UrlRepository.RETRY_TIMES) {
      const random = Math.random().toString(36).substring(2, 8);
      const findPath = await UrlSchema.findOne({ shortPath: random  })
      if (!findPath) {
        shortPath = random
        break;
      }
      tryTime += 1;
    }

    if (!shortPath) {
      throw new BizError('Url generate failed', 500);
    }

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
