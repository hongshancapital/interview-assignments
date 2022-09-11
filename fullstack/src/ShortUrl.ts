import Base62 from "./Base62";

class ShortUrl {
  baseId: number = 10000;
  urlIdMap: Map<number, string> = new Map();      // 此处使用了Map。真实使用时会用数据库来存储

  constructor() {}

  private __getId() {
    return this.baseId++;
  }

  private __shortUrlBuilder(base62Str) {
    return `s.cn/${base62Str}`;
  }

  private __getBase62Str(shortUrl) {
    return shortUrl.replace('s.cn/', '');
  }

  getShortUrl(longUrl: string) {
    const urlId = this.__getId();
    this.urlIdMap.set(urlId, longUrl);
    const base62Str = Base62.toBase62(urlId);
    return this.__shortUrlBuilder(base62Str);
  }

  getLongUrl(shortUrl: string) {
    const base62Str = this.__getBase62Str(shortUrl);
    const urlId = Base62.toBase10(base62Str);
    return this.urlIdMap.get(urlId);
  }
}

export const ShortUrlIns = () => new ShortUrl();

// const shortIns = new ShortUrl();
// const shortUrl1 = shortIns.getShortUrl('www.baidu.com/123/123');
// console.log('shortUrl1: ', shortUrl1);
// console.log('longUrl1: ', shortIns.getLongUrl(shortUrl1));

// const shortUrl2 = shortIns.getShortUrl('www.baidu.com/123/456456');
// console.log('shortUrl2: ', shortUrl2);
// console.log('longUrl2: ', shortIns.getLongUrl(shortUrl2));