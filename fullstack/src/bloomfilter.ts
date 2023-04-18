import { RedisClientType } from 'redis'

export class BloomFilter {
    private client: RedisClientType
    private hashbf: string
    private urlbf: string
    private errorRate: number
    private capacity: number

    constructor(client: RedisClientType, errorRate: number = 0.01, capacity: number = 100000, hashbf: string = 'HASH_BF', urlbf: string = 'URL_BF') {
        this.client = client
        this.hashbf = hashbf
        this.urlbf = urlbf
        this.errorRate = errorRate
        this.capacity = capacity
    }

    async removeAll() {
        await this.client.DEL(this.hashbf);
        await this.client.DEL(this.urlbf);
    }

    async init() {
        this.client.bf.RESERVE(this.hashbf, this.errorRate, this.capacity);
        this.client.bf.RESERVE(this.urlbf, this.errorRate, this.capacity);
    }

    async exist(): Promise<boolean> {
        return await this.client.EXISTS([this.urlbf, this.hashbf]) == 2
    }

    async urlExist(url: string): Promise<boolean> {
        return await this.client.bf.EXISTS(this.urlbf, url)
    }

    async urlAdd(url: string): Promise<void> {
        await this.client.bf.ADD(this.urlbf, url)
    }

    async urlMAdd(urls: string[]): Promise<void> {
        await this.client.bf.MADD(this.urlbf, urls)
    }

    async hashExist(hash: string): Promise<boolean> {
        return await this.client.bf.EXISTS(this.hashbf, hash)
    }

    async hashAdd(hash: string): Promise<void> {
        await this.client.bf.ADD(this.hashbf, hash)
    }

    async hashMAdd(hashes: string[]): Promise<void> {
        await this.client.bf.MADD(this.hashbf, hashes)
    }
}
