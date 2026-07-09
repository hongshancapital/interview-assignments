/**
 * util.test.ts
 */
 import { num10to62, validURL }from  "../src/util/util";

 test('normal convert', () => {
     expect(num10to62('63')).toBe("11");
 });

 test('normal convert', () => {
    expect(num10to62('631')).toBe("ab");
});
test('convert failed', () => {
    expect(num10to62('sss')).toBe("");
});

test('http url format is incorrect', () => {
    expect(validURL('')).toBe(false);
});
test('http url format is incorrect', () => {
    expect(validURL(undefined)).toBe(false);
});

test('http url format is correct', () => {
    expect(validURL('http://www.baidu.com/aaaaa/ccccc')).toBe(true);
});

test('https url format is correct', () => {
    expect(validURL('https://www.baidu.com/aaaaa/ccccc')).toBe(true);
});
test('url format is correct', () => {
    expect(validURL('https://127.0.0.1:8080/aaaaa/ccccc')).toBe(true);
});
test('url format is incorrect', () => {
    expect(validURL('httpsd://www.baidu.com/aaaaa/ccccc')).toBe(false);
});