import { isNumber } from "../utlis/isNumber";

describe("测试isNumber方法", () => {
  test("测试正常数字", () => {
    expect(isNumber(1)).toBeTruthy();
    expect(isNumber(100)).toBeTruthy();
    expect(isNumber(0)).toBeTruthy();
    expect(isNumber(-1)).toBeTruthy();
  });
  test("测试NaN", () => {
    expect(isNumber(NaN)).toBeFalsy();
  });
  test("测试infinite", () => {
    expect(isNumber(Infinity)).toBeFalsy();
    expect(isNumber(-Infinity)).toBeFalsy();
  });
});
