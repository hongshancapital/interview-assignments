import { createShortUrl } from '../../../common/utils/shortUrlCreate.utils'
describe('createShortUrl', () => {
  it('should return a short url string and length less than 9', async () => {
    const result = await createShortUrl();
    expect(result.length).toBeLessThan(9);
    expect(typeof result).toBe('string');
  });
});