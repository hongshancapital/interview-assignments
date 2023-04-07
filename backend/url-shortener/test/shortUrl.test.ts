import {
    createShortUrl,
    IShortUrlParam,
    SHORT_CODE_MAX_LENGTH,
    SHORT_URL_PREFIX,
    StatusCode,
} from '../src/shortUrl';
import { nanoid } from 'nanoid';

const testLongUrl =
    'https://github.com/lyf-coder/interview-assignments/tree/url-shortener';

describe('shortUrl', () => {
    describe('createShortUrl', () => {
        it('no specify shortCode', async () => {
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
            };
            const result = await createShortUrl(shortUrlParam);
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
            const result = await createShortUrl(shortUrlParam);
            expect(result.code).toBe(StatusCode.Success);
            expect(result.shortUrl).toBeDefined();
            expect(result.shortUrl.length).toBe(
                SHORT_CODE_MAX_LENGTH + SHORT_URL_PREFIX.length
            );

            const result2 = await createShortUrl(shortUrlParam);
            expect(result2.code).toBe(StatusCode.Success);
            expect(result2.shortUrl).toBe(result.shortUrl);
        });

        it('specify shortCode', async () => {
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
                shortCode: nanoid(Math.random() * SHORT_CODE_MAX_LENGTH),
            };
            const result = await createShortUrl(shortUrlParam);
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
            const result = await createShortUrl(shortUrlParam);
            expect(result.code).toBe(StatusCode.Success);
            expect(result.shortUrl).toBeDefined();
            expect(result.shortUrl).toBe(
                `${SHORT_URL_PREFIX}${shortUrlParam.shortCode}`
            );

            const result2 = await createShortUrl(shortUrlParam);
            expect(result2.code).toBe(StatusCode.Error);
            expect(result.shortUrl).toBeUndefined();
            expect(result.msg).toBeDefined();
        });

        it('specify overlong shortCode', async () => {
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
                shortCode: nanoid(SHORT_CODE_MAX_LENGTH + 1),
            };
            const result = await createShortUrl(shortUrlParam);
            expect(result.code).toBe(StatusCode.Error);
            expect(result.shortUrl).toBeUndefined();
            expect(result.msg).toBeDefined();
        });

        it('wrong long url format', async () => {
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl.substring(8),
                shortCode: nanoid(SHORT_CODE_MAX_LENGTH + 1),
            };
            const result = await createShortUrl(shortUrlParam);
            expect(result.code).toBe(StatusCode.Error);
            expect(result.shortUrl).toBeUndefined();
            expect(result.msg).toBeDefined();
        });
    });
});
