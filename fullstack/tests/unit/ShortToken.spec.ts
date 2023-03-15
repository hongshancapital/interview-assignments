import assert from "assert";
import splitShortToken from "../../src/ShortToken";

describe("short url token split test: ", () => {
  it("normal url: https://s.cn/aE8gf", () => {
    assert.deepEqual(splitShortToken("https://s.cn/aE8gf"), {
      isLegal: true,
      path: "aE8gf",
    });
  });
  it("unexpected host: https://www.baidu.com/e8gEf", () => {
    assert.deepEqual(splitShortToken("https://www.baidu.com/e8gEf"), {
      isLegal: false,
    });
  });
  it("bad url(random str): ", () => {
    assert.deepEqual(splitShortToken("ababc"), {
      isLegal: false,
    });
  });
});
