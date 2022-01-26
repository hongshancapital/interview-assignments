import { EncodeStr } from '.';

describe('Test Utils', () => {
  it('Check EncodeStr', () => {
    const result = new Set();
    for (let i = 0; i < 1000; i++) {
      result.add(EncodeStr(i));
    }
    const resultArr = Array.from(result);
    expect(resultArr.length).toBe(1000);
    expect(resultArr.some((code: string) => code.length < 6)).toBe(false);
  });
});
