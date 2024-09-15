import config from '@config';

import Redis from 'ioredis';
export const redis = new Redis(config.REDIS_URL);

export async function closeRedis() {
    return redis.quit();
}

export function getRedisKey(...args: Array<string | number | Array<string>>) {
    if (args.length === 1 && Array.isArray(args[0])) {
        return args[0].join(':');
    }

    return args.join(':');
}
