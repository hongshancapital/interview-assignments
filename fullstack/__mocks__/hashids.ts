const hashids: any = jest.createMockFromModule('hashids');
hashids.prototype.encode = jest.fn().mockImplementation(() => 'XE');
hashids.prototype.decode = jest.fn().mockImplementation(() => [1]);
export default hashids;
