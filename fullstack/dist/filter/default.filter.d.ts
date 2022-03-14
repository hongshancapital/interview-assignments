import { Context } from '@midwayjs/koa';
export declare class DefaultErrorFilter {
    catch(err: Error, ctx: Context): Promise<{
        success: boolean;
        message: string;
    }>;
}
