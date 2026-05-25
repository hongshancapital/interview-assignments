import { assert } from "chai";
import { getShortUrl } from "../../../utils/common";

describe("util:getShortUrl", () => {
  it("should return a string", () => {
    const actualUrl = getShortUrl();
    assert.isString(actualUrl);
  });
  it("should length < 8", () => {
    const actualUrl = getShortUrl();
    assert.match(actualUrl, /^[0-9a-zA-Z]{1,8}$/);
  });
});
