import { deepEqual } from "../utils";

describe("test deepEqual method", () => {
  test("should return false when lengths are not the same", () => {
    const result = deepEqual({}, { a: 1 });
    expect(result).toBeFalsy();
  });

  test("should return true when params are same", () => {
    const result = deepEqual({ a: 1 }, { a: 1 });
    expect(result).toBeTruthy();
  });

  test("should return false when params are not the same", () => {
    const result = deepEqual(
      { a: 1, b: { c: { d: 2 } } },
      { a: 1, b: { c: { d: 3 } } }
    );
    expect(result).toBeFalsy();
  });
});
