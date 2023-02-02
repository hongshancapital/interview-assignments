import { ShortUrlProvider } from '../../provider/shortUrl.provider';
import { ShortUrlService } from '../../service/shortUrl.service';

describe('ShortUrlService', () => {
  let shortUrlService: ShortUrlService;
  let shortUrlProvider: ShortUrlProvider;

  beforeEach(() => {
    shortUrlProvider = new ShortUrlProvider();
    shortUrlService = new ShortUrlService(shortUrlProvider);
  });

  it('should saveShortUrl', async () => {
    const longUrl = 'http://www.test.com';
    const spySaveShortUrl = jest.spyOn(shortUrlProvider,'saveShortUrl');

    const result = await shortUrlService.saveShortUrl(longUrl);

    expect(spySaveShortUrl).toHaveBeenCalled();
    expect(result.length).toBeLessThan(9);
    expect(typeof result).toBe('string');
  });

  it('should getShortUrlByLongUrl', async () => {
    const longUrl = 'http://www.test.com';
    const spyGetShortUrlByLongUrl = jest.spyOn(shortUrlProvider,'getShortUrlByLongUrl');

    const result = await shortUrlService.getShortUrlByLongUrl(longUrl);

    expect(spyGetShortUrlByLongUrl).toHaveBeenCalled();
    expect(result.length).toBeLessThan(9);
    expect(typeof result).toBe('string');
  });

  it('should getLongUrlByShortUrl', async () => {
    const shortUrl = 'ZSObExH3';
    const spyGetLongUrlByShortUrl = jest.spyOn(shortUrlProvider,'getLongUrlByShortUrl');

    const result = await shortUrlService.getLongUrlByShortUrl(shortUrl);

    expect(spyGetLongUrlByShortUrl).toHaveBeenCalled();
    expect(typeof result).toBe('string');
    expect(result).toMatch(/^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/);
  });
});