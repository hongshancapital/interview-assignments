import { base10ToBase21, isValidURL, lengthValidator } from "../../util/util";

describe("Unit tests of <util>", () => {
  describe("Test function <base10ToBase21>", () => {
    // Test Helper
    const convert = (c: number) => {
      let ret = -1;
      if (c >= 48 && c <= 57) ret = c - 48;
      if (c >= 97 && c <= 107) {
        ret = c - 97 + 10;
      }
      return ret;
    };
    const base21ToBase10 = (s: string) => {
      let n = 0;
      for (let i = 0; i < s.length; i++) {
        n = n * 21 + convert(s.charCodeAt(i));
      }
      return n;
    };

    test("Normal input - number between 1 to 21^8", () => {
      for (let i = 0; i < 10; i++) {
        const testNb = Math.ceil(Math.random() * Math.pow(21, 8));
        expect(base21ToBase10(base10ToBase21(testNb))).toEqual(testNb);
      }
    }, 60);

    test("Wrong input - number less than 0", () => {
      for (let i = 0; i < 10; i++) {
        const testNb = -Math.ceil(Math.random() * Math.pow(21, 8));
        expect(base10ToBase21(testNb)).toBe("");
      }
    });

    test("Wrong input - number is not integer", () => {
      let testNb: number;
      for (let i = 0; i < 10; i++) {
        do {
          testNb = Math.ceil(Math.random() * Math.pow(21, 8)) / 10;
        } while (Number.isInteger(testNb));
        if (i % 2 !== 0) {
          testNb = -testNb;
        }
        expect(base10ToBase21(testNb)).toBe("");
      }
    }, 60);

    test("Boundry value - input 0", () => {
      expect(base10ToBase21(0)).toBe("");
    }, 60);
  });

  describe("Test function <isValidURL>", () => {
    test("Normal input - valid URLs", () => {
      const testSet = [
        "http://www.baidu.com",
        "https://www.sina.com",
        "http://sohu.com",
        "http://bilibili.io",
        "http://google.cn/article",
        "http://google.hk/article?a=3",
      ];
      for (let i = 0; i < testSet.length; i++) {
        expect(isValidURL(testSet[i])).toBeTruthy();
      }
    }, 60);

    test("Wrong input - invalid URLS", () => {
      const testSet = [
        "httpp://www.baidu.com",
        "https://com",
        "http://sohu.com>?",
      ];
      for (let i = 0; i < testSet.length; i++) {
        expect(isValidURL(testSet[i])).toBeFalsy();
      }
    }, 60);
  });

  describe("Test function <lengthValidator>", () => {
    test("Normal use", () => {
      const result = lengthValidator("fdefrefrv", 10);
      expect(result).toBeTruthy();
    }, 60);
    test("Wrong input - too long", () => {
      const result = lengthValidator("eferf", 2);
      expect(result).toBeFalsy();
    }, 60);
    test("Wrong input - empty url", () => {
      const result = lengthValidator("", 1);
      expect(result).toBeFalsy();
    }, 60);
  });
});
