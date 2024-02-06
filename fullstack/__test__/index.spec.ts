import { expect, test } from '@jest/globals';
import { generateShortUrl, generateLongUrl } from '../service/url';

/**
 * 长域名转短域名已插入情况
 */
test('long url to short url has data', async () => {
    const res = await generateShortUrl('https://www.test.com/aaa/bbb/ccc/index.html')
    expect(res?.data?.url).toBe("https://test.com/sWrJS");
})

/**
 * 长域名转短域名无历史域名情况
 */
test('long url to short url no history', async () => {
    const res = await generateShortUrl(`https://www.test.com/aaa/bbb/ccc/index.html?t=${Date.now()}`)
    expect(res?.code).toBe(0);
})

/**
 * 短域名转长域名正常情况
 */
test('short url to long url success', async () => {
    const res = await generateLongUrl('https://test.com/sWrJS')
    expect(res?.data?.url).toBe("https://www.test.com/aaa/bbb/ccc/index.html")
})


/**
 * 短域名转长域名 查询失败的情况
 */
test('short url to long url not found', async () => {
    const res = await generateLongUrl('https://test.com/1234')
    expect(res?.data?.url).toBe("not found")
})

/**
 * 长域名转短域名 输入空的情况
 */
test('long url to short url input is empty', async () => {
    const res = await generateShortUrl('')
    expect(res?.msg).toBe("input is not a url");
})

/**
 * 短域名转长域名  输入空的情况
 */
test('short url to long url input is empty', async () => {
    const res = await generateLongUrl('')
    expect(res?.msg).toBe("input is not a url")
})

/**
 * 长域名转短域名 输入非链接的情况
 */
test('long url to short url input is not url', async () => {
    const res = await generateShortUrl('qwe')
    expect(res?.msg).toBe("input is not a url");
})

/**
 * 短域名转长域名  输入非链接的情况
 */
test('short url to long url input is not url', async () => {
    const res = await generateLongUrl('qwe')
    expect(res?.msg).toBe("input is not a url")
})

/**
 * 长域名转短域名 输入null的情况
 */
test('long url to short url input is null', async () => {
    const res = await generateShortUrl(null)
    expect(res?.msg).toBe("input is not a url");
})

/**
 * 短域名转长域名  输入null的情况
 */
test('short url to long url input is null', async () => {
    const res = await generateLongUrl(null)
    expect(res?.msg).toBe("input is not a url")
})
