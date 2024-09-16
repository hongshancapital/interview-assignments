import {isShortUrl, isUrl} from "../util";

describe("util test", () => {
  test('isUrl', () => {
    expect(isUrl('https://www.baidu.com')).toBeTruthy();
    expect(isUrl('http://www.baidu.com/a=1')).toBeTruthy();
    expect(isUrl('http://www.baidu.com:8080')).toBeTruthy();
    expect(isUrl('httpa://www.baidu.com:8080/')).toBeFalsy();
  });

  test('isShortUrl', () => {
    expect(isShortUrl('12ab5')).toBeTruthy();
    expect(isShortUrl('12345678')).toBeFalsy();
    expect(isShortUrl('12345&')).toBeFalsy();
  });
});
