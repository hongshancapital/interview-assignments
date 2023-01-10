import { injectable, inject } from 'tsyringe';
import BizError from '@error/BizError';

import IUrlRepository from '@repositories/IUrlRepository';

@injectable()
class ShortenUrlService {
  constructor(
    @inject('UrlRepository')
    private readonly urlRepository: IUrlRepository,
  ) {}

  public async create(originalUrl: string): Promise<string> {
    const newUrl = await this.urlRepository.createShortUrl(originalUrl);
    return newUrl;
  }

  public async find(shortUrl: string): Promise<string> {
    const _url = new URL(shortUrl);
    const shortPath = _url.pathname.slice(1);
    const url = await this.urlRepository.findByShortPath(shortPath);
    if (!url) throw new BizError('Url not found', 404);

    return url;
  }
}

export default ShortenUrlService;
