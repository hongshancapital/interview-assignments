import { ShortId } from "../src/utils/id";

const shortIdObj = new ShortId(19);
test("assert shortId less than or equal 8", () => {
  expect(
    shortIdObj.generateShortId("https://google.com?q=ts").length
  ).toBeLessThanOrEqual(8);
});


