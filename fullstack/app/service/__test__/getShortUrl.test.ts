import { describe, expect, test, jest } from '@jest/globals';
import { DB_RUNTIME_ERROR, HASH_ERROR, BASE62_ERROR, PARAM_ERROR, SUCCESS } from '../../common/errCode';

import { query, insert } from '../../database/db';
import mmh from '../../common/mmh';
import { encodeBase62 } from '../../common/base62';

import { getShortUrl } from '../getShortUrl';
import { DataModal } from '../..';

const mockOriginUrl: string = 'https://www.baidu.com/';
const mockHash: number = 307499014;
const mockShortUrl: string = 'xNptn';

const mockdata: Array<DataModal> = [{
  short_url: mockShortUrl,
  origin_url: mockOriginUrl,
  origin_hash: mockHash
}];

const mockdata2: Array<DataModal> = [{
  short_url: mockShortUrl,
  origin_url: 'https://www.taobao.com',
  origin_hash: mockHash
}];

const mockHash2: number = 307499015;
const emptyMockdata: Array<DataModal> = [];

jest.mock('../../database/db');
const mockedQuery = jest.mocked(query);
const mockedInsert = jest.mocked(insert);

jest.mock('../../common/mmh');
const mockedMmh = jest.mocked(mmh);

jest.mock('../../common/base62');
const mockedBase62 = jest.mocked(encodeBase62);

describe('getShortUrl function', () => {
  test('no shortUrl param', () => {
    return expect(getShortUrl('')).rejects.toBe(PARAM_ERROR);
  });

  test('hash failed', () => {
    mockedMmh.mockImplementation(() => NaN);
    return expect(getShortUrl(mockOriginUrl)).rejects.toBe(HASH_ERROR);
  });

  test('error occurred while querying', () => {
    mockedMmh.mockImplementation(() => mockHash);
    mockedQuery.mockImplementation(() => Promise.reject(DB_RUNTIME_ERROR));
    return expect(getShortUrl(mockOriginUrl)).rejects.toBe(DB_RUNTIME_ERROR);
  });

  test('could find short url', () => {
    mockedQuery.mockImplementation(() => Promise.resolve(mockdata));
    return expect(getShortUrl(mockOriginUrl)).resolves.toBe(mockShortUrl);
  });

  test('base62 failed', () => {
    mockedQuery.mockImplementation(() => Promise.resolve(emptyMockdata));
    mockedBase62.mockImplementation(() => '');
    return expect(getShortUrl(mockOriginUrl)).rejects.toBe(BASE62_ERROR);
  });

  test('error occurred while inserting', () => {
    mockedBase62.mockImplementation(() => mockShortUrl);
    mockedInsert.mockImplementation(() => Promise.reject(DB_RUNTIME_ERROR));
    return expect(getShortUrl(mockOriginUrl)).rejects.toBe(DB_RUNTIME_ERROR);
  });

  test('everything is ok', () => {
    mockedInsert.mockImplementation(() => Promise.resolve(SUCCESS));
    return expect(getShortUrl(mockOriginUrl)).resolves.toBe(mockShortUrl);
  });

  test('could find short url but hash conflicts', () => {
    mockedMmh
      .mockImplementationOnce(() => mockHash)
      .mockImplementationOnce(() => mockHash2)
    ;
    mockedQuery
      .mockImplementationOnce(() => Promise.resolve(mockdata2))
      .mockImplementationOnce(() => Promise.resolve(emptyMockdata))
    ;

    return expect(getShortUrl(mockOriginUrl)).resolves.toBe(mockShortUrl);
  });
});