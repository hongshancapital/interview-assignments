import { bigintToBase62, urlEncode, urlDecode } from '../../../src/utils/transfer';

describe("utils: transfer", () => {

  describe("bigintToBase62", () => {
    it("should return 0 for bigint 0", () => {
      const result = bigintToBase62(0n);
      expect(result).toBe("0");
    });

    it("should return correct result for small positive bigint", () => {
      const result = bigintToBase62(123n);
      expect(result).toBe("1Z");
    });

    it("should return correct result for large positive bigint", () => {
      const result = bigintToBase62(10000000000000000000n);
      expect(result).toBe("bUI6zOLZTri");
    });
  });

  describe("urlEncode", () => {
    it("should return empty string for empty string input", () => {
      const result = urlEncode("");
      expect(result).toBe("");
    });

    it("should return correctly encoded string for input with special characters", () => {
      const result = urlEncode("http://example.com/path?param=value&another_param=value with space");
      expect(result).toBe("http%3A%2F%2Fexample.com%2Fpath%3Fparam%3Dvalue%26another_param%3Dvalue%20with%20space");
    });
  });

  describe("urlDecode", () => {
    it("should return empty string for empty string input", () => {
      const result = urlDecode("");
      expect(result).toBe("");
    });

    it("should return correctly decoded string for input with special characters", () => {
      const result = urlDecode("http%3A%2F%2Fexample.com%2Fpath%3Fparam%3Dvalue%26another_param%3Dvalue%20with%20space");
      expect(result).toBe("http://example.com/path?param=value&another_param=value with space");
    });
  });
  
});
