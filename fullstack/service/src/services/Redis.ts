import redis from '@/tools/redis';

class RedisService {
    constructor() {
        //
    }
    private getCacheDomainKey(shortDomain: string) {
        return `CACHE_domain:${shortDomain}`;
    }

    async getDomain(shortDomain: string): Promise<DomainModel | null> {
        const key = this.getCacheDomainKey(shortDomain);
        const resultStr = await redis.server?.get(key);

        if (!resultStr) {
            return null;
        }
        return JSON.parse(resultStr)[key];
    }

    async setDomain(shortDomain: string, url: string) {
        const key = this.getCacheDomainKey(shortDomain);

        /** 20-21小时 */
        await redis.server?.set(key, url, 'EX', Math.floor(Math.random() * (21 - 20 + 1) + 20) * 60 * 60);
    }

    async deleteDomain(shortDomain: string) {
        const key = this.getCacheDomainKey(shortDomain);

        await redis.server?.del(key);
    }
}

export default new RedisService();
