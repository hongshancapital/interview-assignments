import { Context } from 'koa';
import shortUrlService from '../../service/shortUrlService';

export default {
    async getShortUrl(ctx: Context): Promise<void> {
        try {
            const shortUrl = await shortUrlService.getShortUrl(ctx.request.query.originUrl as string);
            ctx.body = {
                shortUrl
            }
        } catch (error) {
            ctx.body = {
                errorMessage: (error as Error).message
            }
        }
    },

    async getOriginUrl(ctx: Context): Promise<void> {
        try {
            const originUrl = await shortUrlService.getOriginUrl(ctx.request.query.shortUrl as string);
            ctx.body = {
                originUrl
            }
        } catch (error) {
            ctx.body = {
                errorMessage: (error as Error).message
            }
        }
    },
};
