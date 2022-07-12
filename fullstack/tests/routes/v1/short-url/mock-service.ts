jest.unmock('../../../../src/services/ShortUrlService');

export const TEST_KEY = 'Test';
export const TEST_URL = 'https://github.com';

export const findUrlByKey = jest.fn(
  (key: string): string | null => {
    console.log('start find key');
    if(key===TEST_KEY)
    {
      return TEST_URL;
    }
    return null;
  },
);

export const shortenUrl = jest.fn(
  (url : string): string => {
    return TEST_KEY;
  },
);

jest.mock('../../../../src/services/ShortUrlService', () => ({
  get shortenUrl() {
    return shortenUrl;
  },
  get findUrlByKey() {
    return findUrlByKey;
  },
}));
