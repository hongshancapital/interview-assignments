import { isEqual } from "./index";

test("isEqual test basic data types", () => {
  expect(isEqual(1, 1)).toBe(true);
  expect(isEqual(1, 100)).toBe(false);
  expect(isEqual(true, true)).toBe(true);
  expect(isEqual(false, true)).toBe(false);
  expect(isEqual("a", "a")).toBe(true);
  expect(isEqual("a", "cccccc")).toBe(false);
  expect(isEqual("undefined", undefined)).toBe(false);
  expect(isEqual(null, undefined)).toBe(false);
  expect(isEqual(undefined, undefined)).toBe(true);
  expect(isEqual(null, null)).toBe(true);
  expect(isEqual({}, {})).toBe(true);
  expect(isEqual(1, {})).toBe(false);
  expect(isEqual("object", {})).toBe(false);
  expect(isEqual("Object", {})).toBe(false);
  expect(isEqual("{}", {})).toBe(false);
  expect(isEqual([], {})).toBe(false);
});

test("isEqual test object types", () => {
  expect(
    isEqual(
      { a: 1, b: 1, c: { d: { f: 2 }, e: 1, g: [2, 3, { a: 1, b: [2] }] } },
      { a: 1, b: 1, c: { d: { f: 2 }, e: 1, g: [2, 3, { a: 1, b: [2] }] } }
    )
  ).toBe(true);
  expect(isEqual({ a: 1 }, { b: 1 })).toBe(false);
  expect(
    isEqual(
      { a: 1, b: 1, c: { d: { f: 3 }, e: 1 } },
      { a: 1, b: 1, c: { d: { f: 2 }, e: 1 } }
    )
  ).toBe(false);
  expect(
    isEqual(
      { a: 1, b: "1", c: { d: { f: 3 }, e: 1 } },
      { a: 1, b: 1, c: { d: { f: 2 }, e: 1 } }
    )
  ).toBe(false);
  expect(
    isEqual(
      { a: 1, b: "1", c: { d: { f: 3 }, e: 1 }, g: [2, 4] },
      { a: 1, b: 1, c: { d: { f: 2 }, e: 1 }, g: [2, 1] }
    )
  ).toBe(false);
  expect(
    isEqual(
      { a: 1, b: "1", c: { d: { f: 3 }, e: 1 }, g: [2, { a: 1 }] },
      { a: 1, b: 1, c: { d: { f: 2 }, e: 1 }, g: [2, 1] }
    )
  ).toBe(false);
  expect(
    isEqual(
      { a: 1, b: "1", c: { d: { f: 3 }, e: 1 }, g: [2, { a: 1 }] },
      { a: 1, b: 1, c: { d: { f: 2 }, e: 1 }, g: [2, { a: 2 }] }
    )
  ).toBe(false);
});

test("isEqual test array types", () => {
  expect(isEqual([1, "a", [22, [[]]], {}], [1, "a", [22, [[]]], {}])).toBe(
    true
  );
  expect(isEqual([1, "a", [22, [[1]]], {}], ["a", [22, [[10]]], {}])).toBe(
    false
  );
  expect(isEqual([1, "a", [22, [[1]]], {}], ["a"])).toBe(false);
  expect(
    isEqual([1, "a", [22, [[]]], { a: 1 }], [1, "a", [22, [[]]], { a: 2 }])
  ).toBe(false);
});
