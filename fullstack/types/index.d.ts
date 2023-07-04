declare module 'koa';
declare module 'koa-router';
declare module 'nedb';
declare module 'querystring';

/**
 * 返回值的data类型
 */
declare interface ResponseData {
    url?: string;
}

/**
 * 返回值
 */
declare interface ResponseInfo {
    code: number; // 默认为0，代表正常，其余值为异常
    data?: ResponseData;
    msg?: string;
}

/**
 * koa的ctx
 */
declare interface Context {
    originalUrl: string;
    body: ResponseInfo;
}