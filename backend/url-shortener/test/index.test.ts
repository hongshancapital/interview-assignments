import { app } from '../src/app';
import supertest from 'supertest';
import { nanoid } from 'nanoid';
import {
    IReadShortUrlResult,
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

/**
 * 请求读取短域名对应的信息
 * @param shortCode 短域名唯一编码
 * @returns 短域名相关信息
 */
async function readShortUrl(shortCode: string): Promise<IReadShortUrlResult> {
    const res = await request.get(`/${shortCode}`);
    return res.body as IReadShortUrlResult;
}

/**
 * 获取一个符合规范且与传入的短码不同的短码
 * @param shortCode 短域名唯一编码
 * @returns 不同于传入参数的另一个短码
 */
function getDiffShortCode(shortCode: string): string {
    const diffShortCode = nanoid(SHORT_CODE_MAX_LENGTH);
    if (diffShortCode != shortCode) {
        return diffShortCode;
    }
    return getDiffShortCode(shortCode);
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

        it('exist longUrl', async () => {
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
            };
            const result = await postShortUrl(shortUrlParam);
            expect(result.code).toBe(StatusCode.Success);
            expect(result.shortUrl).toBeDefined();
            expect(result.shortUrl.length).toBe(
                SHORT_CODE_MAX_LENGTH + SHORT_URL_PREFIX.length
            );

            const result2 = await postShortUrl(shortUrlParam);
            expect(result2.code).toBe(StatusCode.Success);
            expect(result2.shortUrl).toBe(result.shortUrl);
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

        it('specify exist shortCode', async () => {
            const shortCode = nanoid(Math.random() * SHORT_CODE_MAX_LENGTH);
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
                shortCode,
            };
            const result = await postShortUrl(shortUrlParam);
            expect(result.code).toBe(StatusCode.Success);
            expect(result.shortUrl).toBeDefined();
            expect(result.shortUrl).toBe(
                `${SHORT_URL_PREFIX}${shortUrlParam.shortCode}`
            );

            const result2 = await postShortUrl(shortUrlParam);
            expect(result2.code).toBe(StatusCode.Error);
            expect(result.shortUrl).toBeUndefined();
            expect(result.msg).toBeDefined();
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

    describe('read short url', () => {
        // TODO  need create first
        it('exist shortCode', async () => {
            const shortCode = nanoid(Math.random() * SHORT_CODE_MAX_LENGTH);
            await postShortUrl({ shortCode, longUrl: testLongUrl });
            const result = await readShortUrl(shortCode);
            expect(result.code).toBe(StatusCode.Success);
            expect(result.longUrl).toBe(testLongUrl);
        });

        it('no exist shortCode', async () => {
            const shortCode = nanoid(Math.random() * SHORT_CODE_MAX_LENGTH);
            await postShortUrl({ shortCode, longUrl: testLongUrl });

            const result = await readShortUrl(getDiffShortCode(shortCode));
            expect(result.code).toBe(StatusCode.Error);
            expect(result.longUrl).toBeUndefined();
            expect(result.msg).toBeDefined();
        });

        it('incorrect shortCode - overlong', async () => {
            // overlong shortCode
            const shortCode = nanoid(SHORT_CODE_MAX_LENGTH + 1);
            const result = await readShortUrl(shortCode);
            expect(result.code).toBe(StatusCode.Error);
            expect(result.longUrl).toBeUndefined();
            expect(result.msg).toBeDefined();
        });
    });
});
