import {isValidHttpUrl} from "../../src/utils";

describe("isValidHttpUrl", () => {
    it("test valid url", async () => {
        expect(isValidHttpUrl('http://www.baidu.com')).toBe(true);
    });
});

describe("isValidHttpUrl", () => {
    it("test invalid url", async () => {
        expect(isValidHttpUrl('www.baidu.com')).toBe(false);
    });
});
