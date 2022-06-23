import { describe, it } from "mocha";
import assert from "assert";
import { Shorter } from '../../src/server/lib'
import { ShortUrlModel } from '../../src/models/shortUrl'

describe("Shorter", function () {
  it("test class", async function () {
    const instance = new ShortUrlModel();
    instance.id = 1;
    instance.long_url = 'http://www.baidu.com';
    assert.equal(JSON.stringify(instance), instance.toJSON());
  });
  it("should transform num to str", async function () {
    const useCases = [
      { num: 0, expectStr: 'lL2Rd' },
      { num: 100, expectStr: 'lL22Z' },
      { num: 4319847134712, expectStr: 'MdeWLDqJ' },
      { num: 189, expectStr: 'lL2Tp' },
    ];
    useCases.forEach((useCase: { num: number, expectStr: string }) => {
      const str = Shorter.idToStr(useCase.num)
      assert.equal(str, useCase.expectStr);
    })
  });
  it("should transform str to num", async function () {
    const useCases = [0, 1, 2, 100, 10000, 5452344523];
    useCases.forEach((n: number) => {
      const str = Shorter.idToStr(n)
      const num = Shorter.strToId(str)
      assert.equal(num, n);
    })
  });
});
