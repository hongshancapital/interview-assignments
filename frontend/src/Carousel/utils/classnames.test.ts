import { classnames } from "./classnames";

test("classnames test", () => {
  expect(classnames("class-1", "class-2")).toEqual("class-1 class-2");
  expect(classnames("class-1", ["class-2", "class-1"])).toEqual("class-1 class-2");
  expect(classnames("class-1", ["class-2", "class-1"], { "class-1": false, "class-3": true })).toEqual(
    "class-2 class-3"
  );
});
