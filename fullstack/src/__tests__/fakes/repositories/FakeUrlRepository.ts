import IUrlRepository from '@repositories/IUrlRepository';

interface IUrls {
  originalUrl: string;
  shortPath: string;
}

class FakeUrlRepository implements IUrlRepository {
  private urls: IUrls[] = [];

  public async createShortUrl(originalUrl: string): Promise<string> {
    const shortPath = Math.random().toString(36).substring(2, 8);
    this.urls.push({ originalUrl, shortPath });
    return shortPath;
  }

  public async findByShortPath(shortPath: string): Promise<string | undefined> {
    const findUrl = this.urls.find(url => url.shortPath === shortPath);
    const originalUrl = findUrl?.originalUrl;
    return originalUrl;
  }
}

export default FakeUrlRepository;
