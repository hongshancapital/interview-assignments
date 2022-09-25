import { to62 } from "./to62";

describe("进制转换", () => {
  it("转成 62 进制", () => {
    expect(to62(0)).toBe("0");
    expect(to62(1)).toBe("1");
    expect(to62(9)).toBe("9");

    expect(to62(10)).toBe("a");
    expect(to62(11)).toBe("b");
    expect(to62(35)).toBe("z");

    expect(to62(36)).toBe("A");
    expect(to62(37)).toBe("B");
    expect(to62(61)).toBe("Z");
    expect(to62(62)).toBe("10");
  });
});
