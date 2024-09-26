import { describe, expect, test } from '@jest/globals';
import mmh from '../mmh';

describe('Murmurhash test', () => {
  test('empty input', () => {
    expect(mmh('')).toBe(0);
  });

  test('valid input', () => {
    expect(mmh('message')).toBe(344065877);
  });
});