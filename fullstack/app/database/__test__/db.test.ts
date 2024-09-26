import { describe, expect, test, jest } from '@jest/globals';
import { query, insert, removeByShortUrl } from '../db';
import { SUCCESS, PARAM_ERROR, DB_RUNTIME_ERROR } from '../../common/errCode';
import { DataModal } from '../..';

const mockShortUrl = 'abcdefgh';
const mockdata: DataModal = {
  short_url: mockShortUrl,
  origin_url: 'https://www.baidu.com',
  origin_hash: 1234
};

const querCondition: DataModal = {
  short_url: mockShortUrl
};
const querCondition2: DataModal = {
  origin_hash: 1234
};
const mockdataList: Record<string, any> = [mockdata];
const emptyMockData: Array<DataModal> = [];

jest.spyOn(console, 'log').mockImplementation(() => {});

describe('sqlite3 CURD', () => {
  test('query before inserted', () => {
    return expect(query(querCondition)).resolves.toHaveLength(0);
  });

  test('insert by invalid params', () => {
    return expect(insert()).rejects.toBe(PARAM_ERROR);
  });

  test('insert by valid params', () => {
    return expect(insert(mockdata)).resolves.toBe(SUCCESS);
  });

  test('insert by replicative params', () => {
    return expect(insert(mockdata)).rejects.toBe(DB_RUNTIME_ERROR);
  });

  test('query by invalid params', () => {
    return expect(query()).rejects.toBe(PARAM_ERROR);
  });

  test('query by short url of last inserted params', () => {
    return expect(query(querCondition)).resolves.toMatchObject(mockdataList);
  });
  test('query by hash of last inserted params', () => {
    return expect(query(querCondition)).resolves.toMatchObject(mockdataList);
  });

  test('delete by invalid params', () => {
    return expect(removeByShortUrl()).rejects.toBe(PARAM_ERROR);
  });

  test('delete by last inserted params', () => {
    return expect(removeByShortUrl(mockShortUrl)).resolves.toBe(SUCCESS);
  });

  const table: Array<DataModal> = [{
    short_url: `a' or '1' = '1`
  }, {
    short_url: `a'; drop table url_map; select * from url_map where '1' = '1`
  }];

  test.each(table)('SQL injection', (mock: DataModal) => {
    return expect(query(mock)).resolves.toEqual(emptyMockData);
  });
});