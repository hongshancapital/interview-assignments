import { RedisService } from '../services/redis.service';
export declare class BloomFilterService {
    private readonly redisService;
    private bloomFilter;
    constructor(redisService: RedisService);
    addItemToBloomFilter(item: string): Promise<void>;
    hasItemInBloomFilter(item: string): Promise<any>;
}
