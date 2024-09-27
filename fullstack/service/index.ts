#!/usr/bin/env node

import koa from 'koa';
import Router from 'koa-router';
import http from 'http';
import https from 'https';
import { generateShortUrl, generateLongUrl } from './url';
import qs from 'querystring';
import { Context } from 'koa';

const router = new Router();

/**
 * 两个路由，分别是
 * 短域名存储接口
 * 短域名读取接口
 */
router.get('/longtoshort', async (ctx: Context) => {
    const params: any = qs.parse(ctx.originalUrl.split('?')[1]); // 取query参数
    // console.log('generateShortUrl params', params);
    const res = await generateShortUrl(params.url);
    ctx.body = res;
}).get('/shorttolong', async (ctx: Context) => {
    const params: any = qs.parse(ctx.originalUrl.split('?')[1]);
    // console.log('generateLongUrl params', params)
    const res = await generateLongUrl(params.url);
    ctx.body = res;
})

const app = new koa();
app.use(router.routes()); // 启动路由
app.use(router.allowedMethods()) // 请求错误返回状态

http.createServer(app.callback()).listen(3000);
https.createServer(app.callback()).listen(3001);