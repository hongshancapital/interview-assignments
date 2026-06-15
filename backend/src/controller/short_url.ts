import BaseController from "./base";
import { methodManager } from "../router";
import { ShortUrlService } from '../service/short_url';
import { IContext } from "../types/common";
import { ParamInvalid } from "../middleware/error";

export default class ShortUrlController extends BaseController {
    public shortUrlService: ShortUrlService;

    constructor(ctx: IContext) {
        super(ctx);
        this.shortUrlService = new ShortUrlService(ctx);
    }

    /**
     * 接受长域名信息，返回短域名信息
     * @param request.body.url 长域名 {string}
     */
    @methodManager({path: '/insert', httpMethod: 'POST', middlewares: []})
    public async insert () {
        const url = this.ctx.request.body.url || '';
        try {
            new URL(url);
        } catch (e) {
            throw new ParamInvalid('url invalid');
        }

        const shortUrl = await this.shortUrlService.insertUrl(url);

        return this.ctx.body = {
            short_url: shortUrl
        }
    }

    /**
     * 接受短域名信息，返回长域名信息
     * @param request.query.short_url 短域名 {string}
     */
    @methodManager({path: '/find', httpMethod: 'GET', middlewares: []})
    public async find () {
        if (!this.ctx.request.query.short_url) {
            throw new ParamInvalid('missing short_url');
        }

        let shortUrl: string;

        if (Array.isArray(this.ctx.request.query.short_url)) {
            shortUrl = this.ctx.request.query.short_url[0];
        } else {
            shortUrl = this.ctx.request.query.short_url;
        }

        const longUrl = await this.shortUrlService.find(shortUrl);
        return this.ctx.body = {
            long_url: longUrl
        };
    }
}
