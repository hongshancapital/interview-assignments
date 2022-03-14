import { MidwayHttpError } from '@midwayjs/core';
import { Context } from '@midwayjs/koa';
export declare class NotFoundFilter {
    catch(err: MidwayHttpError, ctx: Context): Promise<void>;
}
