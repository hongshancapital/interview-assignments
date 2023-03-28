import { Next } from "koa";
import { IContext } from "../types/common";

export default async function accessLogger (ctx: IContext, next: Next) {
    const start = Date.now();
    await next();
    const ms = Date.now() - start;
    const code = ctx.status;
    const method = ctx.method;
    const path = ctx.path;
    console.log(`[${method}]${path} - ${ms}ms - ${code}`);
    // TODO 记录详情信息
}