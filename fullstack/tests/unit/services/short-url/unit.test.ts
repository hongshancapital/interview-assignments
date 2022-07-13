import { mockFindByKey, mockAdd } from './mock';
import KeyGenerateService from '../../../../src/services/ShortUrlService'
import { TEST_KEY, TEST_URL } from '../../../constants';

describe('Integrition test for short url api', () => {
  beforeEach(() => {
    mockFindByKey.mockClear();
    mockAdd.mockClear();
  });

  const endpoint = '/v1/short-url';

  // 生成key成功
  it('Generate key successfully.', async () => {

    const key = KeyGenerateService.shortenUrl(TEST_URL);
    expect(key && typeof key === 'string' && key.length <= 8).toBeTruthy();
    expect(mockAdd).toBeCalledTimes(1);
    expect(mockFindByKey).toBeCalledTimes(1);
  });

  //连续生成不同的key
  it('Generate key different.', async () => {

    const key1 = KeyGenerateService.shortenUrl(TEST_URL);
    const key2 = KeyGenerateService.shortenUrl(TEST_URL);

    expect(key1).not.toEqual(key2);
    expect(mockAdd).toBeCalledTimes(2);
    expect(mockFindByKey).toBeCalledTimes(2);
  });

  // 成功找到已生成的key
  it('Find correct url by key', async () => {

    const key = KeyGenerateService.shortenUrl(TEST_URL);
    const url = KeyGenerateService.findUrlByKey(key)

    expect(TEST_URL).toEqual(url);
    expect(mockAdd).toBeCalledTimes(1);
  });

  // key不存在
  it('Find undefined by non-exist key', async () => {

    const url = KeyGenerateService.findUrlByKey(TEST_KEY);

    expect(url).toBeUndefined();
  });
});
