import {describe, expect, test} from '@jest/globals';
import { query } from '../app/storage';

describe('sum module', () => {
  test('query', () => {
    expect(query()).resolves.toHaveLength(0);
  });
});