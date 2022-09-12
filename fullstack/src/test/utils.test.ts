import {
    checkUrlOK,
    getHex32ID,
    addFixedPadding,
    removeALLPadding,
  } from "../utils/utils";
  
  describe("测试 utls", () => {
    test("测试checkUrlOK,正常URL", () => {
      const res = checkUrlOK("http://www.google.com/search?q=java");
      expect(res).toBe(true);
    });
    test("测试checkUrlOK,异常URL", () => {
      const res = checkUrlOK("www.google.com/search?q=java");
      expect(res).toBe(false);
    });
    test("测试checkUrlOK,异常URL2", () => {
      const res = checkUrlOK("h//www.google.com/search?q=java");
      expect(res).toBe(false);
    });
  
    test("测试getHex32ID", () => {
      const res = getHex32ID("http://www.google.com/search?q=go");
      expect(res).toBe("fbf156b8");
    });
  
    test("测试addFixedPadding输入输出", () => {
      const res = addFixedPadding("http://www.google.com/search?q=java");
      expect(res).toBe("http://www.google.com/search?q=java[HASHKEY]");
    });
  
    test("测试removeALLPadding输入输出,无Padding", () => {
      const res = removeALLPadding("http://www.google.com/search?q=java");
      expect(res).toBe("http://www.google.com/search?q=java");
    });
    test("测试removeALLPadding输入输出,1 Padding", () => {
      const res = removeALLPadding(
        "http://www.google.com/search?q=java[HASHKEY]"
      );
      expect(res).toBe("http://www.google.com/search?q=java");
    });
    test("测试removeALLPadding输入输出,n Padding", () => {
      const res = removeALLPadding(
        "http://www.google.com/search?q=java[HASHKEY][HASHKEY][HASHKEY][HASHKEY][HASHKEY]"
      );
      expect(res).toBe("http://www.google.com/search?q=java");
    });
  });
  