import { describe, expect, test } from '@jest/globals';
import { encodeBase62, decodeBase62 } from '../base62';

describe('Base62 test', () => {
  test('encode edge', () => {
    expect(encodeBase62(0)).toBe('0');
    expect(encodeBase62(Math.pow(62, 8) - 1)).toBe('ZZZZZZZZ');
  });

  test('encode invalid input', () => {
    expect(encodeBase62(NaN)).toBe('');
    expect(encodeBase62(-200)).toBe('');
    expect(encodeBase62(Number.MAX_SAFE_INTEGER)).toBe('');
  });

  test('decode edge', () => {
    expect(decodeBase62('0')).toBe(0);
    expect(decodeBase62('ZZZZZZZZ')).toBe(Math.pow(62, 8) - 1);
  });

  test('decode invalid input', () => {
    expect(decodeBase62('ABC%%^@D')).toBe(NaN);
    expect(decodeBase62('ABCDEFGHIJKLMN')).toBe(NaN);
  });

  const table: Array<number> = [1, 1234, 1234567, 1728394719];
  test.each(table)('encode & decode', data => {
    expect(decodeBase62(encodeBase62(data))).toBe(data);
  });

});