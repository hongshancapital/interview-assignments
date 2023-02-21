import { ShortUrl } from "../../entity/ShortUrl"
export class ShortUrlBizError {
    static readonly MAX_ID_LIMIT = 'short-url-biz/max-id-limit';
}

export default class ShortUrlBiz {

    static validateGetUrlParams(params: any, shortUrlBase: string): string | undefined {
        const { short_url } = params
        if ((short_url as string).indexOf(shortUrlBase) !== 0) {
            return 'error: short url is in error format'
        }
    }

    static getIdFromShortUrlString(short_url: string, shortUrlBase: string): number {
        const id36 = short_url.replace(shortUrlBase, '')
        return parseInt(id36, 36)
    }

    static validateCreateBody(body: any, longUrlMaxLength: number): string | undefined {
        const { long_url } = body
        if (!long_url) {
            return 'error: long url is required'
        }
        if (long_url.length > longUrlMaxLength) {
            return `error: long url length is too long, must less than ${longUrlMaxLength}`
        }
    }

    static shortUrlStringFromObj(shortUrl: ShortUrl, shortUrlBase: string) {
        return shortUrlBase + shortUrl.id?.toString(36)
    }

    static validateMaxId(maxId: number): string | undefined {
        if (maxId >= Math.pow(36, 8) - 1) {
            return ShortUrlBizError.MAX_ID_LIMIT
        }
    }

}
