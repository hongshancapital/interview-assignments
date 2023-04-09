import {
    createShortUrl,
    IShortUrlParam,
    readShortUrl,
    SHORT_CODE_MAX_LENGTH,
    SHORT_URL_PREFIX,
    StatusCode,
    ShortUrl,
    IShortUrl,
} from '../src/shortUrl';
import { nanoid } from 'nanoid';
import { ShortUrlError } from '../src/ShortUrlError';
import { db } from '../src/db';
import { cache } from '../src/cache';

const testLongUrl =
    'https://github.com/lyf-coder/interview-assignments/tree/url-shortener';

jest.mock('../src/db');

const mockInsert = jest.fn();
const mockWhere = jest.fn().mockReturnThis();
const mockFirst = jest.fn();

jest.mocked(db).mockReturnValue({
    insert: mockInsert,
    where: mockWhere,
    first: mockFirst,
} as never);

const data: IShortUrl = {
    shortCode: nanoid(8),
    longUrl: testLongUrl,
};

describe('shortUrl', () => {
    describe('ShortUrl class', () => {
        // findByLongUrl 与 findByShortCode 要在 insert上面，不然insert中模拟类中这两个方法会导致测试无法正常执行实际的方法
        describe('findByLongUrl', () => {
            beforeEach(() => {
                jest.clearAllMocks();
            });
            it('normal', async () => {
                await new ShortUrl(data).findByLongUrl();
                expect(mockWhere).toBeCalledWith('longUrl', data.longUrl);
                expect(mockFirst).toBeCalled();
            });
        });
        describe('findByShortCode', () => {
            beforeEach(() => {
                jest.clearAllMocks();
            });
            it('normal', async () => {
                await new ShortUrl(data).findByShortCode();
                expect(mockWhere).toBeCalledWith('shortCode', data.shortCode);
                expect(mockFirst).toBeCalled();
            });
        });
        describe('insert', () => {
            beforeEach(async () => {
                jest.clearAllMocks();
                cache.clear();
            });
            it('normal', async () => {
                await new ShortUrl(data).insert();
                expect(mockInsert).toBeCalled();
            });

            it('longUrl exist', async () => {
                const findByLongUrlMock = jest
                    .spyOn(ShortUrl.prototype, 'findByLongUrl')
                    .mockResolvedValue(data);
                mockInsert.mockRejectedValue('存储错误！');
                const result = await new ShortUrl(data).insert();
                expect(result).toBe(data.shortCode);
                expect(mockInsert).toBeCalledTimes(1);
                expect(findByLongUrlMock).toBeCalledTimes(1);
            });
            it('shortCode exist', async () => {
                const findByLongUrlMock = jest
                    .spyOn(ShortUrl.prototype, 'findByLongUrl')
                    .mockResolvedValue(undefined);

                const findByShortCodeMock = jest
                    .spyOn(ShortUrl.prototype, 'findByShortCode')
                    .mockResolvedValue(data);
                mockInsert
                    .mockRejectedValueOnce('存储错误！')
                    .mockImplementationOnce(jest.fn());

                const result = await new ShortUrl({
                    shortCode: 'aaa',
                    longUrl: testLongUrl,
                }).insert();
                expect(result).toBeDefined();
                expect(mockInsert).toBeCalledTimes(2);
                expect(findByLongUrlMock).toBeCalledTimes(1);
                expect(findByShortCodeMock).toBeCalledTimes(1);
            });
            it('error', async () => {
                mockInsert.mockRejectedValueOnce('存储错误！');
                const findByLongUrlMock = jest
                    .spyOn(ShortUrl.prototype, 'findByLongUrl')
                    .mockResolvedValue(undefined);
                const findByShortCodeMock = jest
                    .spyOn(ShortUrl.prototype, 'findByShortCode')
                    .mockResolvedValue(undefined);
                try {
                    await new ShortUrl({
                        shortCode: nanoid(8),
                        longUrl: testLongUrl,
                    }).insert();
                } catch (e) {
                    expect(e).toStrictEqual(
                        new ShortUrlError('存储短域名信息失败！')
                    );
                }
                expect(mockInsert).toBeCalledTimes(1);
                expect(findByLongUrlMock).toBeCalledTimes(1);
                expect(findByShortCodeMock).toBeCalledTimes(1);
            });
        });
    });
    describe('createShortUrl', () => {
        const findByShortCodeMock = jest.spyOn(
            ShortUrl.prototype,
            'findByShortCode'
        );
        beforeEach(() => {
            findByShortCodeMock.mockClear();
            cache.clear();
        });
        it('normal', async () => {
            const shortUrlInsertMock = jest
                .spyOn(ShortUrl.prototype, 'insert')
                .mockResolvedValue(data.shortCode);
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
            };
            const result = await createShortUrl(shortUrlParam);
            expect(result.code).toBe(StatusCode.Success);
            expect(result.shortUrl).toBeDefined();
            expect(shortUrlInsertMock).toBeCalled();
        });

        it('specify shortCode no exist', async () => {
            const shortUrlInsertMock = jest
                .spyOn(ShortUrl.prototype, 'insert')
                .mockResolvedValue(data.shortCode);
            shortUrlInsertMock.mockClear();
            findByShortCodeMock.mockResolvedValue(undefined);
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
                shortCode: data.shortCode,
            };

            const result = await createShortUrl(shortUrlParam);
            expect(result.code).toBe(StatusCode.Success);
            expect(result.shortUrl).toBeDefined();
            expect(result.shortUrl).toBe(
                `${SHORT_URL_PREFIX}${shortUrlParam.shortCode}`
            );
            expect(findByShortCodeMock).toBeCalled();
            expect(shortUrlInsertMock).toBeCalled();
        });

        it('specify exist shortCode', async () => {
            const shortUrlInsertMock = jest.spyOn(ShortUrl.prototype, 'insert');
            shortUrlInsertMock.mockClear();

            const shortCode = nanoid(Math.random() * SHORT_CODE_MAX_LENGTH);
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
                shortCode,
            };
            findByShortCodeMock.mockResolvedValue(data);

            try {
                await createShortUrl(shortUrlParam);
            } catch (e) {
                expect(e).toStrictEqual(new ShortUrlError('短码已存在！'));
            }
            expect(findByShortCodeMock).toBeCalled();
            expect(shortUrlInsertMock).not.toBeCalled();
        });

        it('specify exist shortCode in cache', async () => {
            const shortUrlInsertMock = jest.spyOn(ShortUrl.prototype, 'insert');
            shortUrlInsertMock.mockClear();
            const shortCode = nanoid(Math.random() * SHORT_CODE_MAX_LENGTH);
            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
                shortCode,
            };
            cache.set(shortCode, testLongUrl);

            try {
                await createShortUrl(shortUrlParam);
            } catch (e) {
                expect(e).toStrictEqual(new ShortUrlError('短码已存在！'));
            }
            expect(findByShortCodeMock).not.toBeCalled();
            expect(shortUrlInsertMock).not.toBeCalled();
        });

        it('specify overlong shortCode', async () => {
            const shortUrlInsertMock = jest.spyOn(ShortUrl.prototype, 'insert');
            shortUrlInsertMock.mockClear();

            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl,
                shortCode: nanoid(SHORT_CODE_MAX_LENGTH + 1),
            };
            try {
                await createShortUrl(shortUrlParam);
            } catch (e) {
                expect(e).toStrictEqual(new ShortUrlError('短码过长！'));
            }

            expect(shortUrlInsertMock).not.toBeCalled();
        });

        it('wrong long url format', async () => {
            const shortUrlInsertMock = jest.spyOn(ShortUrl.prototype, 'insert');
            shortUrlInsertMock.mockClear();

            const shortUrlParam: IShortUrlParam = {
                longUrl: testLongUrl.substring(8),
                shortCode: nanoid(SHORT_CODE_MAX_LENGTH + 1),
            };
            try {
                await createShortUrl(shortUrlParam);
            } catch (e) {
                expect(e).toStrictEqual(
                    new ShortUrlError('长域名格式不正确！')
                );
            }
            expect(shortUrlInsertMock).not.toBeCalled();
        });
    });

    describe('readShortUrl', () => {
        const findByShortCodeMock = jest.spyOn(
            ShortUrl.prototype,
            'findByShortCode'
        );
        beforeEach(() => {
            findByShortCodeMock.mockClear();
            cache.clear();
        });
        it('exist shortCode', async () => {
            findByShortCodeMock.mockResolvedValue(data);
            const result = await readShortUrl(data.shortCode);
            expect(result.code).toBe(StatusCode.Success);
            expect(result.longUrl).toBe(testLongUrl);
            expect(findByShortCodeMock).toBeCalled();
        });

        it('exist shortCode in cache', async () => {
            const shortCode = nanoid(Math.random() * SHORT_CODE_MAX_LENGTH);
            cache.set(shortCode, testLongUrl);
            const result = await readShortUrl(shortCode);
            expect(result.code).toBe(StatusCode.Success);
            expect(result.longUrl).toBe(testLongUrl);
            expect(findByShortCodeMock).not.toBeCalled();
        });

        it('no exist shortCode normal format', async () => {
            findByShortCodeMock.mockResolvedValue(undefined);
            try {
                await readShortUrl(data.shortCode);
            } catch (e) {
                expect(e).toStrictEqual(
                    new ShortUrlError('未找到对应的长域名！')
                );
            }
            expect(findByShortCodeMock).toBeCalled();
        });

        it('incorrect shortCode - overlong', async () => {
            // overlong shortCode
            const shortCode = nanoid(SHORT_CODE_MAX_LENGTH + 1);
            try {
                await readShortUrl(shortCode);
            } catch (e) {
                expect(e).toStrictEqual(new ShortUrlError('短码过长！'));
            }
            expect(findByShortCodeMock).not.toBeCalled();
        });

        it('incorrect shortCode - length-0', async () => {
            try {
                await readShortUrl('');
            } catch (e) {
                expect(e).toStrictEqual(new ShortUrlError('短码不能为空！'));
            }
            expect(findByShortCodeMock).not.toBeCalled();
        });
    });
});
