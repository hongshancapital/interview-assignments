import { IMiddleware } from '@midwayjs/core';
import { NextFunction, Context } from '@midwayjs/koa';
export declare class ReportMiddleware implements IMiddleware<Context, NextFunction> {
    resolve(): (ctx: Context, next: NextFunction) => Promise<any>;
    static getName(): string;
}
