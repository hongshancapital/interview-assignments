/**
 * longtoshort.test.ts
 */
 import {longToShort, redis}from  "../src/service/longtoshort";

test('longUrl is null', async () => {
     expect(await longToShort(null)).toBe("");
 });
test('longUrl is undefined', async () => {
    expect(await longToShort(undefined)).toBe("");
});

/**
 * Redis 中事先插入 ABCD - baidu.com 的key-value
 * 预计返回短域名ABCD
 */
test('longUrl has exsited, return the shorturl from Redis', async () => {
    expect(await longToShort("http://www.baidu.com/ssssyyysss/shhss")).toBe("https://t.cn/ABCD");
});
/**
 * 对于新长域名，取得Redis自增序列的值后，得到对应的62进制字符串
 * 将短域名和长域名插入Redis后，序列增1，之后返回短域名
 * 自增序列初始值：10000001
 */
 test('longUrl does not exsit, create a new short url and return', async () => {
    expect(await longToShort("http://www.baidu.com/aaaaa/ccccc")).toBe("https://t.cn/FXsl");
});
afterAll(async () => {
    await redis.quit();
});