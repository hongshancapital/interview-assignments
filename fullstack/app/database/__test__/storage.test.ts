import { describe, expect, test } from '@jest/globals';
import { query, insert, remove } from '../db';
import { SUCCESS, PARAM_ERROR } from '../../common/errCode';

const mockdata: any = {
  short_url: 'abcdefgh',
  origin_url: 'https://www.baidu.com',
  origin_hash: 'F9751DE431104B125F48DD79CC55822A'
};

describe('sqlite3 CURD', () => {
  test('insert by invalid params', () => {
    return expect(insert()).rejects.toBe(PARAM_ERROR);
  });

  test('insert by valid params', () => {
    return expect(insert(mockdata)).resolves.toBe(SUCCESS);
  });

  test('insert by replicative params', () => {
    return expect(insert(mockdata)).rejects.toBe(PARAM_ERROR);
  });

  test('query by invalid params', () => {
    return expect(query()).rejects.toBe(PARAM_ERROR);
  });

  test('query by last inserted params', () => {
    return expect(query(mockdata)).resolves.toMatchObject([mockdata]);
  });

  test('delete by invalid params', () => {
    return expect(remove()).rejects.toBe(PARAM_ERROR);
  });

  test('delete by last inserted params', () => {
    return expect(remove(mockdata)).resolves.toBe(SUCCESS);
  });
});