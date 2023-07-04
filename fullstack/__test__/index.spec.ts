import { expect, test } from '@jest/globals';
import { generateShortUrl, generateLongUrl } from '../service/url';


test('short url to long url', async () => {
    const res = await generateShortUrl('https://www.test.com/aaa/bbb/ccc/index.html')
    expect(res?.data?.url).toBe("https://test.com/e904db93");
})

test('long url to short url', async () => {
    const res = await generateLongUrl('https://test.com/e904db93')
    expect(res?.data?.url).toBe("https://www.test.com/aaa/bbb/ccc/index.html")
})