import { describe, expect, test, jest } from '@jest/globals';
import { NOT_FOUND, DB_RUNTIME_ERROR, HASH_ERROR, PARAM_ERROR } from '../../common/errCode';

import { query, insert } from '../../database/db';
import mmh from '../../common/mmh';

import { getShortUrl } from '../getShortUrl';
import { DataModal } from '../..';

const mockdata: Array<DataModal> = [{
  short_url: 'xNptn',
  origin_url: 'https://www.baidu.com/',
  origin_hash: 307499014
}];

const mockUrl: string = 'https://www.baidu.com/';

const emptyMockdata: Array<DataModal> = [];

jest.mock('../../database/db');
const mockedQuery = jest.mocked(query);
const mockedInsert = jest.mocked(insert);

jest.mock('../../common/mmh');
const mockMmh = jest.mocked(mmh);

describe('getShortUrl function', () => {
  test('no shortUrl param', () => {
    expect(getShortUrl('')).rejects.toBe(PARAM_ERROR);
  });

  test('hash failed', () => {
    mockMmh.mockImplementation(() => NaN);
    expect(getShortUrl(mockUrl)).rejects.toBe(HASH_ERROR);
  });

  // test('error occurred while querying', () => {
  // });

  // test('could find short url', () => {
  // });

  // test('could find short url but hash conflicts', () => {
  // });

  // test('base62 failed', () => {
  // });

  // test('error occurred while querying', () => {
  // });

  // test('everything is ok', () => {
  // });
});