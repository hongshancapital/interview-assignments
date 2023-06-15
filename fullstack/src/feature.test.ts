import { toLong, toShort, _fallback2UniqueId } from "./feature";

describe('feature test', () => {
  test('long and short', () => {
    const source = [
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
    ];
    const short = source.map(toShort);
    const long = short.map(toLong);
    expect(long).toEqual(source);
  });

  test('fallback', () => {
    const source = [
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
      `http://a.com/${Math.floor(Math.random() * 1000)}`,
    ];
    const short = source.map(_fallback2UniqueId);
    const long = short.map(toLong);
    expect(long).toEqual(source);
  })
});
