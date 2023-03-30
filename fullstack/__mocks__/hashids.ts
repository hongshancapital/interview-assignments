import { NOT_EXIST_URL } from '../src/util.js';

const hashids: any = jest.createMockFromModule('hashids');
hashids.prototype.encode = jest.fn().mockImplementation(() => 'XE');
hashids.prototype.decode = jest.fn().mockImplementation((hash) => {
  if (hash === NOT_EXIST_URL) {
    return [];
  }
  return [1];
});
export default hashids;
