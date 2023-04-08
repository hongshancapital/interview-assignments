import {
    createShortUrl,
    IShortUrlParam,
    readShortUrl,
    SHORT_CODE_MAX_LENGTH,
    SHORT_URL_PREFIX,
    StatusCode,
    ShortUrl,
} from '../src/shortUrl';
import { nanoid } from 'nanoid';
import { ShortUrlError } from '../src/ShortUrlError';
import {
    closeDb,
    createShortUrlTable,
    getDb,
    loadDb,
    SHORT_URL_TABLE,
} from '../src/db';
import { getDiffShortCode } from '../src/util';
import { cache } from '../src/cache';

const testLongUrl =
    'https://github.com/lyf-coder/interview-assignments/tree/url-shortener';

describe('shortUrl', () => {
    beforeAll(async () => {
        loadDb({
            client: 'sqlite3',
            connection: {
                filename: './data-unit.db',
            },
        });
        await createShortUrlTable();
    });
    beforeEach(async () => {
        // 清除表内容
        await getDb()(SHORT_URL_TABLE).del();
        // 清除缓存
        cache.clear();
    });
    describe('ShortUrl class', () => {
        it('insert-longUrl exist', async () => {
            const shortCode = nanoid(8);
            const shortCode2 = nanoid(8);

            // 先插入一条数据
            await new ShortUrl({
                shortCode,
                longUrl: testLongUrl,
            }).insert();
            // 再次插入一条短码不同，长域名相同的数据
            const result = await new ShortUrl({
                shortCode: shortCode2,
                longUrl: testLongUrl,
            }).insert();
            expect(result).toBe(shortCode);
        });
        it('insert- shortCode exist', async () => {
            const shortCode = nanoid(8);

            // 先插入一条数据
            await new ShortUrl({
                shortCode,
                longUrl: testLongUrl,
            }).insert();
            // 再次插入一条短码相同，长域名不同的数据
            const result = await new ShortUrl({
                shortCode: shortCode,
                longUrl: testLongUrl + 'TEST',
            }).insert();
            expect(result !== shortCode).toBeTruthy();
        });
        it('insert - error', async () => {
            const shortCode = nanoid(8);

            // 先插入一条数据
            const shortUrlObj = new ShortUrl({
                shortCode,
                longUrl: testLongUrl,
            });
            await shortUrlObj.insert();
            // 再次插入一条短码相同，长域名不同的数据
            try {
                const result = await new ShortUrl(
                    {
                        shortCode: shortCode,
                        longUrl: testLongUrl + 'TEST',
                    },
                    0
                ).insert();
                expect(result).toBeUndefined();
            } catch (e) {
                expect(e).toStrictEqual(
                    new ShortUrlError('存储短域名信息失败！')
                );
            }
        });
    });
    describe('createShortUrl', () => {
        it('no specify shortCode', async () => {
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
            };
            const result = await createShortUrl(shortUrlParam);
            expect(result.code).toBe(StatusCode.Success);
            expect(result.shortUrl).toBeDefined();
            console.log(result);
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

            try {
                const result = await createShortUrl(shortUrlParam);
                expect(result).toBeUndefined();
            } catch (e) {
                expect(e).toStrictEqual(new ShortUrlError('短码已存在！'));
            }
        });

        it('specify exist shortCode in cache', async () => {
            const shortCode = nanoid(Math.random() * SHORT_CODE_MAX_LENGTH);
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
                shortCode,
            };
            cache.set(shortCode, testLongUrl);

            try {
                const result = await createShortUrl(shortUrlParam);
                expect(result).toBeUndefined();
            } catch (e) {
                expect(e).toStrictEqual(new ShortUrlError('短码已存在！'));
            }
        });

        it('specify overlong shortCode', async () => {
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
                shortCode: nanoid(SHORT_CODE_MAX_LENGTH + 1),
            };
            try {
                const result = await createShortUrl(shortUrlParam);
                expect(result).toBeUndefined();
            } catch (e) {
                expect(e).toStrictEqual(new ShortUrlError('短码过长！'));
            }
        });

        it('wrong long url format', async () => {
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl.substring(8),
                shortCode: nanoid(SHORT_CODE_MAX_LENGTH + 1),
            };
            try {
                const result = await createShortUrl(shortUrlParam);
                expect(result).toBeUndefined();
            } catch (e) {
                expect(e).toStrictEqual(
                    new ShortUrlError('长域名格式不正确！')
                );
            }
        });
    });

    describe('readShortUrl', () => {
        it('exist shortCode', async () => {
            const shortCode = nanoid(Math.random() * SHORT_CODE_MAX_LENGTH);
            await createShortUrl({ shortCode, longUrl: testLongUrl });
            const result = await readShortUrl(shortCode);
            expect(result.code).toBe(StatusCode.Success);
            expect(result.longUrl).toBe(testLongUrl);
        });

        it('no exist shortCode', async () => {
            const shortCode = nanoid(Math.random() * SHORT_CODE_MAX_LENGTH);
            await createShortUrl({ shortCode, longUrl: testLongUrl });

            try {
                const result = await readShortUrl(getDiffShortCode(shortCode));
                expect(result).toBeUndefined();
            } catch (e) {
                expect(e).toStrictEqual(
                    new ShortUrlError('未找到对应的长域名！')
                );
            }
        });

        it('incorrect shortCode - overlong', async () => {
            // overlong shortCode
            const shortCode = nanoid(SHORT_CODE_MAX_LENGTH + 1);
            try {
                const result = await readShortUrl(shortCode);
                expect(result).toBeUndefined();
            } catch (e) {
                expect(e).toStrictEqual(new ShortUrlError('短码过长！'));
            }
        });

        it('incorrect shortCode - length-0', async () => {
            try {
                const result = await readShortUrl('');
                expect(result).toBeUndefined();
            } catch (e) {
                expect(e).toStrictEqual(new ShortUrlError('短码不能为空！'));
            }
        });
    });
    afterAll(async () => {
        await getDb().schema.dropTableIfExists(SHORT_URL_TABLE);
        await closeDb();
    });
});
