import MemoryCache from "./memoryCache"
import randomKey from "./randomKey"

export default class ShortUrlService {
  generalSortUrl = (url: string): string => {
    let key = randomKey(8);
    let exists = MemoryCache.getInstance().getValue(key);
    let maxRetry = 10;
    while (exists && maxRetry > 0) {
      key = randomKey(8);
      exists = MemoryCache.getInstance().getValue(key);
      maxRetry -= 1;
    }
    MemoryCache.getInstance().setValue(key, url);
    return key;
  }

  getOriginalUrl = (key: string): string | undefined => {
    return MemoryCache.getInstance().getValue(key);
  }
}