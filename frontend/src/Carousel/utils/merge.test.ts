import { merge } from "./merge";

test("object merge test", () => {
  const source = { a: 1, b: 2 };
  const target = { b: 3, c: 4 };
  const result = merge<Record<string, number>>(source, target);

  expect(result).toEqual({ a: 1, b: 3, c: 4 });
  expect(result).not.toBe(source);
});

test("array merge test", () => {
  const source = [1, 2, { a: 1, b: 2 }];
  const target = [3, 2, { a: 1, d: 2 }, 4];
  const result = merge<any[]>(source, target);

  expect(result).toEqual([3, 2, { a: 1, b: 2, d: 2 }, 4]);
  expect(result).not.toBe(source);
});

test("null source merge", () => {
  const target = { a: 1, b: 2 };
  const result = merge(null, target);
  expect(result).toBe(target);
});

test("none source merge", () => {
  const target = { a: 1, b: 2 };
  const result = merge(undefined, target);
  expect(result).toBe(target);
});

test("wrong types merge", () => {
  const source = {};
  const target: any[] = [];
  const result = merge(source, target);
  expect(result).toBe(source);
});
