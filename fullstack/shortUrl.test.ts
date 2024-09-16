import { describe, expect, test } from '@jest/globals';
import { shortToLong, longToShort } from './shortUrl';

describe('sum module', () => {
  test('test1', () => {
    var sourceUrl: string = 'https://www.baidu.com/s?wd=node';
    var short: string = longToShort(sourceUrl);
    console.log(`short for ${sourceUrl}`, short);
    var long: string = shortToLong(short);
    console.log(`long for ${short}`, shortToLong(short));
    expect(long == sourceUrl);
  });
  test('null', () => {
    var sourceUrl: string = 'https://mis.jiedaibao.com/#/mis/projectModule';
    var short: string = longToShort(sourceUrl);
    console.log(`short for ${sourceUrl}`, short);
    var long: string = shortToLong(short);
    console.log(`long for ${short}`, shortToLong(short));
    expect(long == sourceUrl);
  });
});