import { Controller } from 'egg';
export default class SiteController extends Controller{
    /**
     * 获取短域名
     * @returns 
     */
    public async encodeUrl(): Promise<void> {
        const {ctx, app} = this;
        const query = ctx.query;
        const validator = app.validator.site.encodeUrl;
        const { error } = ctx.validate(validator, query, { allowUnknown: true }, false);
        if (error) {
            return ctx.jsonResponse.error(app.errorCode.Parameter, error.message);
        }
        try {
            //已是短域名，未解析域名用的IP
            if (query.url.includes(ctx.request.host.split(':')[0])) {
                ctx.jsonResponse.success(query.url);
                return;
            }
            const res = await ctx.service.siteService.encodeUrl(query.url);
            ctx.jsonResponse.success(res);
        } catch (e) {
            ctx.jsonResponse.error(app.errorCode.SystemInvalid, JSON.stringify((<Error>e).message));
            this.logger.error(e);
        } 
    }

    /**
     * 解析短域名
     * @returns 
     */
    public async decodeUrl(): Promise<void> {
        const {ctx, app} = this;
        const params = ctx.params;
        try {
            const res = await ctx.service.siteService.decodeUrl(params.id);
            ctx.jsonResponse.success(res);
        } catch (e) {
            ctx.jsonResponse.error(app.errorCode.SystemInvalid, JSON.stringify((<Error>e).message));
            this.logger.error(e);
        } 
    }
}