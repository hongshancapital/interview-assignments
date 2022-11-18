import { RedisClientType } from 'redis'

export class BloomFilter {
    private client: RedisClientType
    constructor(client: RedisClientType) {
        this.client = client
    }
}
