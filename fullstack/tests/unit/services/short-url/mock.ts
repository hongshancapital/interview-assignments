jest.unmock('../../../../src/database/repository/ShortUrlRepo');
//在这边stub 一个db是按照正常有数据库的情况进行测试
const  mockDB: { [key: string]: string } = {};

export const mockFindByKey = jest.fn(
  (key: string): string | null => {
    return mockDB[key];
  },
);

export const mockAdd = jest.fn(
  async (key:string, url:string) => {
    mockDB[key] = url;
  },
);

jest.mock('../../../../src/database/repository/ShortUrlRepo', () => ({
  get findByKey() {
    return mockFindByKey;
  },
  get Add() {
    return mockAdd;
  },
}));