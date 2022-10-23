import {describe, expect, test} from '@jest/globals';
import { query, insert, remove } from '../app/storage';
import { PARAM_ERROR } from '../app/errMap';
import { DataModal } from '../app';

const mockdata: DataModal = {
  short_url: 'abcdefgh',
  origin_url: 'https://www.baidu.com',
  origin_md5: 'F9751DE431104B125F48DD79CC55822A'
};

describe('sqlite3 CURD', () => {
  test('insert by invalid params', () => {
    expect(insert()).rejects.toBe(PARAM_ERROR);
  });

  test('insert by valid params', () => {
    expect(insert(mockdata)).resolves.toBe(true);
  });

  test('insert by replicative params', () => {
    expect(insert(mockdata)).rejects.toBe(PARAM_ERROR);
  });

  test('query by invalid params', () => {
    expect(query()).rejects.toBe(PARAM_ERROR);
  });

  test('query by last inserted params', () => {
    expect(query(mockdata)).resolves.toHaveLength(1);
  });

  test('delete by invalid params', () => {
    expect(remove()).rejects.toBe(PARAM_ERROR);
  });

  test('delete by last inserted params', () => {
    expect(remove(mockdata)).resolves.toBe(true);
  });
});