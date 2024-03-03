import * as redis from "redis";
import {BASE_URL, EXPIRE_TIME, GENERATOR_KEY} from "../server";
import {GetMainFrameLastShort} from "./postgres_setup";
import assert from "assert";
import {GetNextShorterByCurrent} from "./generate_shorter";


const REDIS_URL = process.env.REDIS_URL || 'redis://localhost:6379';
export const RedisClient = redis.createClient({url: REDIS_URL});

// 这里同样属于上游组件，业务不应在此处做任何限制
// RedisClient.on('error', (err) => {
//     console.log('Redis Client Error', err);
// });

(async () => {
    await RedisClient.connect();
}) ();

export async function SyncGeneratorToRedis() {
    const synSym = await RedisClient.exists(GENERATOR_KEY);
    if (synSym === 0) {
        let currentShorter = await GetMainFrameLastShort();
        await RedisClient.set(GENERATOR_KEY, currentShorter)
    }
}

export async function DispatchShort(longName: string) {
    const current = await RedisClient.get(GENERATOR_KEY);
    // 不对上游做任何形式的验证
    assert(current);

    const next = GetNextShorterByCurrent(current);
    await RedisClient.multi()
        .set(current, longName,{EX: EXPIRE_TIME})
        .set(GENERATOR_KEY, next)
        .exec();

    return {
        longDomain: longName,
        shortDomain: BASE_URL + current
    };
}
