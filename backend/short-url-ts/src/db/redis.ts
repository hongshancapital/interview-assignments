import {createClient, RedisClientType} from 'redis';
import {redis as redisConfig} from "../config/config_datasource";
import * as Util from "../util";
import config from '../config/config';

let client: RedisClientType;

export async function connectRedis(): Promise<void> {
    if (!redisConfig.host || !redisConfig.port) {
        Util.throwErr('500_config_err', 'redis配置信息缺失', 'ER230220094436');
    }
    client = createClient({
        socket: {
            port: redisConfig.port,
            host: redisConfig.host
        },
        password: redisConfig.password || ''
    });

    // client.on('error', err => {
    //     console.error(`[Redis]DB connection failed.${err && err.message}`);
    // });
    client.on("ready", function () {
        console.log('[Redis]DB connection has been established successfully.');
    });
    client.on("end", function () {
        console.log('[Redis]DB connection end successfully.');
    });
    await client.connect().catch(err => {
        console.error(`[Redis]DB connection failed.${err && err.message}`);
        Util.throwErr('500_redis_err', 'redis连接失败', 'ER230220095543');
    });
}

export async function closeRedis(): Promise<void> {
    // graceful close
    await client.quit();
}

export async function set(key: string, value: string, expires?: number): Promise<void> {
    expires = expires || 12 * 3600;

    await client.set(`${config.cachePrefix}:${key}`, value, {'EX': expires});
    console.log(`[redis]set success:${key};${value};${expires}`);
}

export async function get(key: string): Promise<string> {
    const val = await client.get(`${config.cachePrefix}:${key}`);
    console.log(`[redis]get success:${key};${val}`);
    return val;
}