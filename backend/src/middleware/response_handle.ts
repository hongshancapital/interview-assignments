import { IContext, ICustomError } from "../types/common";
import { Next } from "koa";

export default async function responseHandle (ctx: IContext, next: Next) {
    try {
        await next();

        const resultData = ctx.body;
        const result = {
            data: resultData,
            meta: {
                server_time: Date.now(),
                code: 200,
                message: ''
            },
        };

        ctx.body = result;
    } catch (e) {
        errorHandler(ctx, e);
    }
}

function errorHandler (ctx: IContext, err: ICustomError & Error) {
    const meta = {
        statusCode: 500,
        errorCode: 10000,
        message: 'internal server error.'
    };

    if (err.statusCode && err.errorCode && err.desc) {
        meta.statusCode = err.statusCode;
        meta.errorCode = err.errorCode;
        meta.message = err.desc;
    } else {
        // 打印非预期错误
        console.error(err);
        // TODO 格式化记录与统计错误
    }

    ctx.status = meta.statusCode;
    ctx.body = {
        data: ctx.body ? ctx.body : {},
        meta: {
            server_time: Date.now(),
            code: meta.errorCode,
            message: meta.message,
        },
    };
}
