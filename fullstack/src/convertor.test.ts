import { decode, encode } from './convertor';

describe('test convertor', () => {
  test('encode and decode', () => {
    const byte = Math.floor(0xfffffff * Math.random());
    const string = encode(byte);
    const hex = decode(string);
    expect(hex).toBe(byte);
  })
})