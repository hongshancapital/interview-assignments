import {IdTranslator} from "../id-translator";

describe("id-translator test", () => {
  const idTranslator = new IdTranslator()
  test("encode and decode", () => {
    const v = 1;
    const hash = idTranslator.encode(v);
    expect(hash).toBeTruthy()
    const id = idTranslator.decode(hash);
    expect(id).toEqual(v)
  })

  test("encode length", () => {
    const hash = idTranslator.encode(1);
    expect(hash.length).toEqual(5)
    const hash2 = idTranslator.encode(2147483647);
    expect(hash2.length).toEqual(7)
  });
});
