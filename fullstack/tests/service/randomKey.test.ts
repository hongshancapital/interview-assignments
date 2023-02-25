import randomKey from "../../src/service/randomKey";

describe("randomKey", () => {
  it('get randomKey', () => {
    const key = randomKey(3);
    expect(key).toHaveLength(3);
    const key2 = randomKey(3);
    expect(key2).toHaveLength(3);
    expect(key).not.toEqual(key2);
    const key3 = randomKey(5);
    expect(key3).toHaveLength(5);
  });
});