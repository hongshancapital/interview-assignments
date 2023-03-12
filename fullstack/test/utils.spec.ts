/*
 * @Author: zhangyan
 * @Date: 2023-03-10 17:42:11
 * @LastEditTime: 2023-03-10 17:49:55
 * @LastEditors: zhangyan
 * @FilePath: /fullstack/test/utils.spec.ts
 * @Description: utils测试
 */
import { genToken } from "../src/utils/genToken";

describe("genToken", () => {
  describe("gentoken", () => {
    it("test gentoken is a base62 string", async () => {
      expect(genToken()).toMatch(/^[a-zA-Z0-9]{1,8}$/);
    });
  });
});
