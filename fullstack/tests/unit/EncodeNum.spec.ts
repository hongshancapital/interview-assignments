import assert from "assert";
import encodeNum from "../../src/EncodeNum";
describe("convert number from decimal to 63", () => {
  it("convert 0: ", () => {
    assert.equal(encodeNum(0), "0");
  });
  it("covert 100: ", () => {
    // 100 - 63 = 37
    assert.equal(encodeNum(100), "1C");
  });
  it("covert 1000: ", () => {
    assert.equal(encodeNum(1000), "g8");
  });
});
