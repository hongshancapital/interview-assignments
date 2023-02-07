import { ShortUrlProvider } from '../../provider/shortUrl.provider';
import { DbConnection } from '../../common/db/dbConnectionOrm.db';

describe('ShortUrlProvider', () => {
  let shortUrlProvider: ShortUrlProvider;
  let dbConnection: DbConnection;

  beforeAll(async () => {
    dbConnection = await DbConnection.getInstance();
    shortUrlProvider = new ShortUrlProvider();
  });

  describe('saveShortUrl', () => {
    it('should save a new short url', async () => {
      const shortUrl = 'ZSObExH6';
      const longUrl = 'http://www.test6.com';
      const result = await shortUrlProvider.saveShortUrl(shortUrl, longUrl);
      expect(result).toEqual(shortUrl);
    });
  });

  describe('getShortUrlByLongUrl', () => {
    it('should get a short url by long url', async () => {
      const shortUrl = 'ZSObExH6';
      const longUrl = 'http://www.test6.com';
      const result = await shortUrlProvider.getShortUrlByLongUrl(longUrl);
      expect(result).toEqual(shortUrl);
    });
  });

  describe('getLongUrlByShortUrl', () => {
    it('should get a long url by short url', async () => {
      const shortUrl = 'ZSObExH6';
      const longUrl = 'http://www.test6.com';
      const result = await shortUrlProvider.getLongUrlByShortUrl(shortUrl);
      expect(result).toEqual(longUrl);
    });
  });
});