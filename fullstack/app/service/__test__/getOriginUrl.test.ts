import { describe, expect, test, jest } from '@jest/globals';
import { getOriginUrl } from '../getOriginUrl';
import { NOT_FOUND, PARAM_ERROR } from '../../common/errCode';
import { DataModal } from '../..';

const mockdata: Array<DataModal> = [{
  short_url: 'abcdefgh',
  origin_url: 'https://www.baidu.com',
  origin_hash: 3912604874
}];

const emptyMockdata: Array<DataModal> = [];

jest.mock('../../database/db', () => {
  return {
    query: jest.fn()
      .mockImplementationOnce(() => Promise.reject(NOT_FOUND))
      .mockImplementationOnce(() => Promise.resolve(emptyMockdata))
      .mockImplementationOnce(() => Promise.resolve(mockdata))
  }
});

describe('getOriginUrl function', () => {
  test('no shortUrl param', () => {
    return expect(getOriginUrl('')).rejects.toBe(PARAM_ERROR);
  });

  test('error occurred while querying', () => {
    return expect(getOriginUrl('someurl')).rejects.toBe(NOT_FOUND);
  });

  test('could not find origin url', () => {
    return expect(getOriginUrl('someurl')).rejects.toBe(NOT_FOUND);
  });

  test('could find origin url', () => {
    return expect(getOriginUrl('someurl')).resolves.toBe(mockdata[0].origin_url);
  });
});