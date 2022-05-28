import { equal, ok } from 'assert';
import shortUrlService from '../../services/shortUrl';
let testUrlId: string;

describe('shortUrlService', function () {

  before(async function () {
    await shortUrlService.init_sync();
  });

  describe('#createShortUrl()', function () {
    it('should return urlId', async function () {
      testUrlId = await shortUrlService.createShortUrl('http://baidu.com');
      equal(testUrlId.length > 0, true);
    });
  });

  describe('#queryUrl()', function () {
    it(`should return dbNo + urlId equal last test result`, async function () {
      const result = await shortUrlService.queryUrl('http://baidu.com');
      equal(result, testUrlId);
    });
  });

  describe('#queryShortUrl()', function () {
    it(`should return url equal http://baidu.com`, async function () {
      const result = await shortUrlService.queryShortUrl(testUrlId.substring(0, 2), testUrlId.substring(2));
      equal(result, 'http://baidu.com');
    });
  });

});
