import { RedisClientType, defineScript } from 'redis'
import crypto from 'crypto'

export class Cache {
    private client: RedisClientType

    constructor(client: RedisClientType) {
        this.client = client
    }

    async set(hash: string, url: string): Promise<string | null> {
        return await this.client.SET(hash, url,{
            PX: 5000
        })
    }

    async get(hash: string): Promise<string | null> {
        return await this.client.GET(hash)
    }

    async tryLock(lockKey: string): Promise<string | null> {
        const lockValue: string = crypto.randomBytes(20).toString('hex')
        const c: string | null = await this.client.SET(lockKey, lockValue, {
            NX: true,
            PX: 3000
        })
        return c == 'OK' ? lockValue : null
    }

    async releaseLock(lockKey: string, lockValue: string): Promise<boolean> {
        const sc = defineScript({
            NUMBER_OF_KEYS: 1,
            SCRIPT: `
            if redis.call("get",KEYS[1]) == ARGV[1]
            then
                return redis.call("del",KEYS[1])
            else
                return 0
            end
            `,
            transformArguments(key: string, value: string) {
                return [key, value]
            },
            transformReply(reply: number): number {
                return reply;
            }
        })
        return await this.client.scriptsExecuter(sc, [lockKey, lockValue]) == 1
    }
}
