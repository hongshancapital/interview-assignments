import { describe, expect, it } from '@jest/globals';

import Application from "../../../src/app";

import ShortUrlController from "../../../src/controller/short_url";
import { ParamInvalid } from "../../../src/middleware/error";

describe('shortUrlController', () => {
    let shortUrlController: ShortUrlController;

    beforeEach(async () => {
        const app = new Application();
        const ctx = app.createAnonymousContext();
        shortUrlController = new ShortUrlController(ctx);
    });

    describe('method', () => {
        it('should insert url', async () => {
            shortUrlController.ctx.request.body = {
                url: 'https://baidu.com/xxxxxxx'
            };

            const shortUrl = 'https://x.cn/a';

            jest.spyOn(shortUrlController.shortUrlService, 'insertUrl')
                .mockImplementation(async (): Promise<string> => Promise.resolve(shortUrl));

            const result = await shortUrlController.insert();
            expect(result.short_url).toBe(shortUrl);
        });


        it('should find url', async () => {
            jest.spyOn(shortUrlController.shortUrlService, 'find')
                .mockImplementation(async (): Promise<string> => Promise.resolve(longUrl));

            shortUrlController.ctx.request.query = {
                short_url: 'https://x.cn/a'
            }

            const longUrl = 'https://baidu.com/xxxxxxx';

            const result = await shortUrlController.find();
            expect(result.long_url).toBe(longUrl);
        });

        it('should handle invalid url', async () => {
            shortUrlController.ctx.request.body = {
                url: '!@*'
            };
            await expect(shortUrlController.insert()).rejects.toThrow(ParamInvalid);

            shortUrlController.ctx.request.query = {
                short_url: ''
            }
            await expect(shortUrlController.find()).rejects.toThrow(ParamInvalid);
        });
    });
})
