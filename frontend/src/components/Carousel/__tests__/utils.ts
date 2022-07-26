import { formatList } from "../utils";

test("formatList", () => {
  expect(formatList("xPhone")).toEqual(["xPhone"]);
  expect(formatList(["xPhone", "Tablet"])).toEqual(["xPhone", "Tablet"]);
});
