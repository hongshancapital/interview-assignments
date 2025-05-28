import { ShortUrlIns } from '../ShortUrl';
import Base62 from '../Base62';

let shorUrlCon;
let toBase62Spy;
let toBase10Spy;
const tempLongUrl1 = 'www.qq.com/longurl/1';
const tempLongUrl2 = 'www.qq.com/longurl/2';
const secondBase62Str = 's.cn/2Bj';


beforeAll(() => {
  shorUrlCon = ShortUrlIns();
  toBase62Spy = jest.spyOn(Base62, 'toBase62');
  toBase10Spy = jest.spyOn(Base62, 'toBase10');
});

afterAll(() => {
  toBase62Spy.mockRestore();
  toBase10Spy.mockRestore();
});

/**
 * @author thornwang
 * @priority P0
 * @casetype unit
 */
describe('getShortUrl', () => {
  
  
  it('when longUrl is enter, toBase62 should be called', () => {
    const shortUrl = shorUrlCon.getShortUrl(tempLongUrl1);

    expect(toBase62Spy).toHaveBeenCalled();
    expect(shortUrl).toBe('s.cn/2Bi');
  });

  it('when second longUrl is enter, base62Str should be 2Bj', () => {
    const shortUrl = shorUrlCon.getShortUrl(tempLongUrl2);

    expect(shortUrl).toBe(secondBase62Str);
  });
});

/**
 * @author thornwang
 * @priority P0
 * @casetype unit
 */
describe('getLongUrl', () => {
  it('when tempShortUrl is enter, toBase10 should be called', () => {
    const longUrl = shorUrlCon.getLongUrl(secondBase62Str);

    expect(toBase10Spy).toHaveBeenCalled();
    expect(longUrl).toBe(tempLongUrl2);
  });
});