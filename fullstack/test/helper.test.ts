import { map, mapChar, INVALID_CHAR } from "../src/helper";

test("mapChar", () => {
    expect(mapChar(-1)).toBe(INVALID_CHAR);
    expect(mapChar(0)).toBe('0');
    expect(mapChar(9)).toBe("9");
    expect(mapChar(10)).toBe("A");
    expect(mapChar(35)).toBe("Z");
    expect(mapChar(36)).toBe("a");
    expect(mapChar(61)).toBe("z");
    expect(mapChar(62)).toBe(INVALID_CHAR);
});

test("map", () => {
    expect(map(-1)).toBe("");
    expect(map(0)).toBe('0');
    expect(map(1)).toBe("1");
    expect(map(61)).toBe("z");
    expect(map(62)).toBe("10");
    expect(map(218340105584895)).toBe("zzzzzzzz");
    expect(map(218340105584896)).toBe("");
});
