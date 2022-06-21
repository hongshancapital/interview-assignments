import { Storage } from "../index";

test("direct get url expect undefined", () => {
  const randomUrl = `${Math.random() * 10000}`;
  expect(Storage.get(randomUrl)).toBe(undefined);
});

test("save same url expect same key", () => {
  const randomUrl = `${Math.random() * 10000}`;
  let key = Storage.add(randomUrl);
  expect(Storage.add(randomUrl)).toBe(key);
});

test("get url by key will success", () => {
  const randomUrl = `${Math.random() * 10000}`;
  let key = Storage.add(randomUrl);
  expect(Storage.get(key)).toBe(randomUrl);
});
