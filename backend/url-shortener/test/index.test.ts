import { app } from '../src/app';
import supertest from 'supertest';
import { nanoid } from 'nanoid';
import {
    IShortUrlParam,
    IShortUrlResult,
    SHORT_CODE_MAX_LENGTH,
    SHORT_URL_PREFIX,
    StatusCode,
} from '../src/shortUrl';

const request = supertest(app);
const testLongUrl =
    'https://github.com/lyf-coder/interview-assignments/tree/url-shortener';

/**
 * 请求转换短链接
 * @param shortUrlParam 请求转换短链接参数
 * @returns 转换结果
 */
async function postShortUrl(
    shortUrlParam: IShortUrlParam
): Promise<IShortUrlResult> {
    const res = await request.post('/shortUrl').send(shortUrlParam);
    return res.body as IShortUrlResult;
}

describe('url shortener api', () => {
    describe('create short url', () => {
        it('no specify shortCode', async () => {
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
            };
            const result = await postShortUrl(shortUrlParam);
            expect(result.code).toBe(StatusCode.Success);
            expect(result.shortUrl).toBeDefined();
            expect(result.shortUrl.length).toBe(
                SHORT_CODE_MAX_LENGTH + SHORT_URL_PREFIX.length
            );
        });

        it('specify shortCode', async () => {
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
                shortCode: nanoid(Math.random() * SHORT_CODE_MAX_LENGTH),
            };
            const result = await postShortUrl(shortUrlParam);
            expect(result.code).toBe(StatusCode.Success);
            expect(result.shortUrl).toBeDefined();
            expect(result.shortUrl).toBe(
                `${SHORT_URL_PREFIX}${shortUrlParam.shortCode}`
            );
        });

        it('specify overlong shortCode', async () => {
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
                shortCode: nanoid(SHORT_CODE_MAX_LENGTH + 1),
            };
            const result = await postShortUrl(shortUrlParam);
            expect(result.code).toBe(StatusCode.Error);
            expect(result.shortUrl).toBeUndefined();
            expect(result.msg).toBeDefined();
        });

        it('wrong long url format', async () => {
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl.substring(8),
                shortCode: nanoid(SHORT_CODE_MAX_LENGTH + 1),
            };
            const result = await postShortUrl(shortUrlParam);
            expect(result.code).toBe(StatusCode.Error);
            expect(result.shortUrl).toBeUndefined();
            expect(result.msg).toBeDefined();
        });
    });
});
