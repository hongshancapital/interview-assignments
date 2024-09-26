import { createClient } from 'redis'

import cfg  from "../config/config"

// 创建Redis客户端创建
const redisClient = createClient({url: cfg.redisUrl,})

export class RedisCache {

    private constructor() {}
    
    public static RedisObj() {
        const cache:RedisCache =  new RedisCache(); 
        return cache;
    } 

    async initCache() {
        await redisClient.connect();
    }

    async UnitCache() {
        await redisClient.disconnect();
    }

    async GetVal(cacheKey: any) {
        return await redisClient.get(cacheKey);
    }

    async SetVal(cacheKey: any, cacheVal: any) {
        return await redisClient.setEx(cacheKey, cfg.cacheTime, cacheVal);
    }

    async BfAdd(bfVal: any) {
        return await redisClient.sendCommand(['BF.ADD', cfg.urlFilter, bfVal])
    }

    async BfExists(bfVal: any) {
        const ret = await redisClient.sendCommand(['BF.EXISTS', cfg.urlFilter, bfVal])
        return ret == 1;
    }
}

export const redisCache = RedisCache.RedisObj(); 