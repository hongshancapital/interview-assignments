import { ResUtil } from "../index";

describe("ResUtil test", () => {
  test("test showResult", () => {
    expect(ResUtil.showResult({})).toStrictEqual({
      code: 0,
      msg: "success",
      data: {},
    });
  });

  test("test showError", () => {
    expect(ResUtil.showError({})).toStrictEqual({
      code: 10000,
      msg: "failed",
      data: {},
    });
  });
});
