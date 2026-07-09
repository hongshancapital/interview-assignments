import { URL } from 'url';
import config from 'config'
import { Context } from 'koa';
import redis from '../lib/redis';
import { spawnShortKey } from '../lib/shortKey';
import { DomainInfo } from '../@types';

// 根据短码获取长链
async function getDomainByShortCode (shortCode: string): Promise<DomainInfo | void> {
    // 长链信息的key
    const shortDomainKey = config.get('redis-key.short-url') as string;
    const value = await redis.hget(shortDomainKey, shortCode);
    if (value) {
        return JSON.parse(value);
    }
}

/**
 * 生成短码
 * - 为什么不先查缓存再去设置？
 *   - 生成的短码有可能会根据业务线、投放渠道等计算pv、uv等信息
*/
export async function spawnShortCode (ctx: Context): Promise<void> {
    const {
        url, query,
    } = ctx.request.body as Record<string, any>;

    // 生成短码shortCode
    const shortIdKey = config.get('redis-key.short-id') as string;
    const shortId = await redis.incr(shortIdKey);
    const shortCode = spawnShortKey(shortId);

    // 保存短码信息
    const info: DomainInfo = {
        url, query,
    };
    const shortUrlKey = config.get('redis-key.short-url') as string;
    await redis.hset(shortUrlKey, shortCode, JSON.stringify(info));

    ctx.body = {
        code: 0,
        data: {
            shortCode,
            ...info,
        },
    };
}

/**
 * 获取短码信息
*/
export async function getShortCodeInfo (ctx: Context): Promise<void> {
    const {
        shortCode,
    } = ctx.request.query;

    const info = await getDomainByShortCode(shortCode as string);
    if (info) {
        ctx.body = {
            code: 0,
            data: info
        };
    } else {
        ctx.status = 404;
        ctx.body = { code: 404 };
    }
}

/**
 * 重定向到真实的url
*/
export async function redirectRealUrl(ctx: Context): Promise<void> {
    const {
        shortCode,
    } = ctx.params;
    
    const info = await getDomainByShortCode(shortCode as string);

    if (info) {
        const query = info.query;
        const url = new URL(info.url);
        for (let key in query) {
            url.searchParams.set(key, query[key]);
        }
        ctx.redirect(url.toString());
    }
    // 无数据-404兜底页面
}
