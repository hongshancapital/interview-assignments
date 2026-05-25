import { Context, Next } from 'koa';
import * as service from '@/service'
import { getUrlByShortId } from '@/service';

export async function generateShortUriHandler(ctx: Context, next: Next) {
    const originalUrl = ctx.request.body?.url

    if (originalUrl) {

        const shortId = await service.getShortId(originalUrl)


        ctx.body = {
            uri: `/${shortId}`
        }
    } else {
        ctx.response.status = 400
        ctx.response.body = 'Illegal Request'
    }

}

export async function getUrlByShortIdHandler(ctx: Context, next: Next) {
    const shortId = ctx.params?.shortId;
    if (shortId) {
        const url = (await getUrlByShortId(shortId));
        if (url) {
            ctx.body = {
                url
            }
        }
    } else {
        ctx.response.status = 404;
        ctx.response.body = 'Not Found'
    }
}