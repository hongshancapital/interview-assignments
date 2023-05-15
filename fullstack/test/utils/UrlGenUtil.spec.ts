import { generateShortUrl } from '../../utils/UrlGenUtil';

test('Url生成工具', () => {
  expect(generateShortUrl().length).toBeLessThan(13); // 包含5位域名部分
});
