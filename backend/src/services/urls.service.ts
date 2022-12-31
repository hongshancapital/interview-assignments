import { HttpException } from '@exceptions/HttpException';
import { ShortUrl } from '@/interfaces/urls.interface';
import { generateShort } from '@utils/util';
import ShortUrlModel from '@/models/urls.model';

class UrlService {
  // 说明: 为方便运行，这里使用一个数组代替真实DB
  public urls = ShortUrlModel;

  public async findAllUrl(): Promise<ShortUrl[]> {
    const urls: ShortUrl[] = this.urls;
    return urls;
  }

  public async findByShortUrl(url: string): Promise<ShortUrl> {
    const findUrl: ShortUrl = this.urls.find(item => item.short === url);
    if (!findUrl) throw new HttpException(409, "Url doesn't exist");
    return findUrl;
  }

  public async createWithLongUrl(url: string): Promise<ShortUrl> {
    const findUrl: ShortUrl = this.urls.find(item => item.long === url);
    if (findUrl) throw new HttpException(409, `Url already exist`);

    // 确保 DB 里面的 short url 唯一
    let short: string = '';
    while(true) {
        const tmp = generateShort();
        const has: ShortUrl = this.urls.find(item => item.short === tmp);
        if (!has) {
            short = tmp;
            break;
        }
    }
    const createUrlData: ShortUrl = { id: this.urls.length + 1, short, long: url };
    this.urls = [...this.urls, createUrlData];
    return createUrlData;
  }
}

export default UrlService;
