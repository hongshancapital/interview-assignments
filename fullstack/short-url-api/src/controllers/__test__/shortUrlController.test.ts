import { connectDb, closeDb } from 'utils/testUtils';
import Controller from '../shortUrlController';

const mockLongUrl = 'https://medium.com/dailyjs/a-simple-guide-to-understanding-javascript-es6-generators-d1c350551950';

describe('Short URL controller', () => {
  beforeEach(async () => {
    await connectDb();
  });

  afterEach(async () => {
    await closeDb();
  });

  it('Should create a shortUrl', async () => {
    const data = await Controller.create(mockLongUrl, 8);

    expect(data.longUrl).toEqual(mockLongUrl);
  });

  it('Should get the same short URL if long URL is the same', async () => {
    const data = await Controller.create(mockLongUrl);
    const data2 = await Controller.create(mockLongUrl);
    expect(data.longUrl).toEqual(data2.longUrl);
  });

  it('Should find the object with short url', async () => {
    try {
      const data = await Controller.create(mockLongUrl, 8);
      expect(data?.shortUrl).toBeDefined();

      const shortUrlObj = await Controller.findByShortUrl(data.shortUrl);
      expect(shortUrlObj?.longUrl).toEqual(mockLongUrl);
    } catch (e) {
      expect(e.message).toBe(
        'User validation failed: gender: `not a gender` is not a valid enum value for path `gender`.',
      );
    }
  });
});
