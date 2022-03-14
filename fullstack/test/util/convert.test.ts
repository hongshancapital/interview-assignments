import { IdToShortURL, ShortUrlToId } from '../../src/util/convert';
describe('test/util/convert.test.ts', () => {

  it('test convert', async () => {
    // create app
    const shortUrl = IdToShortURL(1000000)
    // console.log(shortUrl)
    expect(shortUrl).toBe('emjc')

    const id = ShortUrlToId('emjc')
    // console.log(id)
    expect(id).toBe(1000000)
  });

});
