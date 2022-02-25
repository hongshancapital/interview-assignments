import { createClient } from 'redis'
import {BloomFilter}  from 'bloomfilter'

import cfgs  from "../config/config"

// Redis缓存
const redisClient = createClient({url: cfgs.redisUrl,})

// 布隆过滤器缓存
const blfile = new BloomFilter(cfgs.bfbyte, cfgs.bfHashCnt);

export class RedisCache {

    constructor() {}
    
    async initCache() {
        await redisClient.connect()
    }

    async GetVal(cacheKey: any) {
        return await redisClient.get(cacheKey);
    }

    async SetVal(cacheKey: any, cacheVal: any) {
        return await redisClient.setEx(cacheKey, cfgs.cacheTime, cacheVal);
    }

    async BfAdd(bfVal: any) {
        return await blfile.add(bfVal);
    }

    async BfExists(bfVal: any) {
        return await blfile.test(bfVal);
    }
}