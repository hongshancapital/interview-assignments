import { describe, expect, test } from '@jest/globals';
import {
  POSTFIX,
  SHORT_DOMAIN,
  addPostfix,
  removePostfix,
  addShortDomain,
  removeShortDomain
} from '../url';

describe('url postfix test', () => {
  const url: string = 'https://www.baidu.com';
  const urlWithPostfix: string = url + POSTFIX;

  test('addPostfix invalid', () => {
    expect(addPostfix('')).toBe('');
  });

  test('addPostfix valid', () => {
    expect(addPostfix(url)).toBe(urlWithPostfix);
  });

  test('removePostfix valid', () => {
    expect(removePostfix('')).toBe('');
    expect(removePostfix(urlWithPostfix)).toBe(url);
  });

  test('addPostfix & removePostfix', () => {
    expect(removePostfix(addPostfix(url))).toBe(url);

    let resultUrl: string = url;
    const testTimes: number = 4;

    for (let i = 0; i < testTimes; i++) {
      resultUrl = addPostfix(resultUrl);
    }
    for (let i = 0; i < testTimes; i++) {
      resultUrl = removePostfix(resultUrl);
    }

    expect(resultUrl).toBe(url);
  });
});

describe('url domain test', () => {
  const shortUrl: string = 'x35Ta';
  const shortUrlWithDomain: string = SHORT_DOMAIN + shortUrl;

  test('addDomain invalid', () => {
    expect(addShortDomain('')).toBe('');
  });

  test('addPostfix valid', () => {
    expect(addShortDomain(shortUrl)).toBe(shortUrlWithDomain);
  });

  test('removeDomain valid', () => {
    expect(removeShortDomain('')).toBe('');
    expect(removeShortDomain(shortUrlWithDomain)).toBe(shortUrl);
  });

  test('addPostfix & removeDomain', () => {
    expect(removeShortDomain(addShortDomain(shortUrl))).toBe(shortUrl);
  });
});