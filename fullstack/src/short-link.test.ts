import { afterAll, describe, expect, it } from '@jest/globals';
import { sql } from './db';
import { getKeyFromURL, getURLFromKey } from './short-link';

describe('short-link', () => {
  it('set and get', async () => {
    const url = 'https://www.baidu.com';
    const key = await getKeyFromURL(url);
    expect(url).toEqual(await getURLFromKey(key));
  });

  it('error when invalid url', async () => {
    expect(getKeyFromURL('htt://xxx')).rejects.toBeInstanceOf(Error);
  });

  it('error when invalid key', async () => {
    expect(getURLFromKey('0')).rejects.toBeInstanceOf(Error);
  });

  it('add new url', async () => {
    const url = 'https://www.baidu.com/' + Math.random();
    const key = await getKeyFromURL(url);
    expect(url).toEqual(await getURLFromKey(key));
  });
});

afterAll(() => {
  return sql.end();
});
