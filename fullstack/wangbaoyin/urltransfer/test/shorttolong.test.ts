/**
 * shorttolong.test.ts
 */
 import {shortToLong,redis} from  "../src/service/shorttolong";


test('shortUrl is null', async () => {
     expect(await shortToLong(null)).toBe("");
 });
test('shortUrl is undefined', async () => {
    expect(await shortToLong(undefined)).toBe("");
});
/**
 * Redis 中事先插入 ABCD - baidu.com 的key-value
 * 预计返回短域名ABCD
 */
 test('shortUrl has exsited, return the long url from Redis', async () => {
    expect(await shortToLong("https://t.cn/ABCD")).toBe("http://www.baidu.com/ssssyyysss/shhss");
});

test('shortUrl does not exsit, end', async () => {
    expect(await shortToLong("https://t.cn/NBLJK")).toBe(null);
});
afterAll(async () => {
    await redis.quit();
});