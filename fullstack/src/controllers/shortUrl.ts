import { ShortUrlModel, ShortUrlDocument } from '../models/shortUrl';

const crypto = require('crypto');

export class ShortUrlController {
  async createShortUrl(longUrl: string): Promise<string | null> {
    const existingShortUrl = await ShortUrlModel.findOne({ longUrl }).exec();

    if (existingShortUrl) {
      return existingShortUrl.shortUrl;
    }

    const shortUrl = this.generateShortUrl(longUrl);
    const shortUrlDoc: ShortUrlDocument = new ShortUrlModel({ longUrl, shortUrl });

    try {
      await shortUrlDoc.save();
      return shortUrl;
    } catch (e: any) {
      console.error(e.message);
      return null;
    }
  }

  async getLongUrl(shortUrl: string): Promise<string | null> {
    const shortUrlDoc = await ShortUrlModel.findOne({ shortUrl }).exec();
    return shortUrlDoc ? shortUrlDoc.longUrl : null;
  }

  private toBase62(num: number) {
    const chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    let res = '';
    do {
      res = chars[num % 62] + res;
      num = Math.floor(num / 62);
    } while (num > 0);
    return res;
  }

  private generateShortUrl(longUrl: string): string {
    const salt = crypto.randomBytes(8).toString('hex');
    const hash = crypto.createHash('sha256').update(longUrl + salt).digest('hex');
    const num = parseInt(hash, 16);
    const shortUrl = this.toBase62(num).substr(0, 8);
    return shortUrl;
  }
}
