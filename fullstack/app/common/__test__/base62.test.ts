import { describe, expect, test } from '@jest/globals';
import { encodeBase62 } from '../base62';

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

  test('encode valid', () => {
    expect(encodeBase62(1)).toBe('a');
    expect(encodeBase62(12)).toBe('m');
    expect(encodeBase62(1234)).toBe('wU');
  });
});