// import { createClient, RedisClientType } from "redis"
import * as redis from "redis"

import { RedisConfig } from "../model/conf"
import logger from "../lib/logger"

export default async function initRedis(): Promise<redis.RedisClientType> {
    const redisConfig: RedisConfig = {
        url: process.env.REDIS || "redis://:auth@localhost:6379",
    }
    try {
        const redisCli: redis.RedisClientType = redis.createClient(redisConfig)
        await redisCli.connect()
        return redisCli
    } catch (error) {
        logger.error(`Redis init error: ${error}`)
        throw error
    }
}
