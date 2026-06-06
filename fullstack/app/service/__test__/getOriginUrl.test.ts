import { describe, expect, test, jest } from '@jest/globals';
import { getOriginUrl } from '../getOriginUrl';
import { DB_RUNTIME_ERROR, NOT_FOUND, PARAM_ERROR } from '../../common/errCode';
import { query } from '../../database/db';
import { DataModal } from '../..';

const mockdata: Array<DataModal> = [{
  short_url: 'abcdefgh',
  origin_url: 'https://www.baidu.com',
  origin_hash: 3912604874
}];

const emptyMockdata: Array<DataModal> = [];

jest.mock('../../database/db');
const mockedQuery = jest.mocked(query);

describe('getOriginUrl function', () => {
  test('no shortUrl param', () => {
    return expect(getOriginUrl('')).rejects.toBe(PARAM_ERROR);
  });

  test('error occurred while querying', () => {
    mockedQuery.mockImplementation(() => Promise.reject(DB_RUNTIME_ERROR));
    return expect(getOriginUrl('someurl')).rejects.toBe(DB_RUNTIME_ERROR);
  });

  test('could not find origin url', () => {
    mockedQuery.mockImplementation(() => Promise.resolve(emptyMockdata));
    return expect(getOriginUrl('someurl')).rejects.toBe(NOT_FOUND);
  });

  test('could find origin url', () => {
    mockedQuery.mockImplementation(() => Promise.resolve(mockdata));
    return expect(getOriginUrl('someurl')).resolves.toBe(mockdata[0].origin_url);
  });
});