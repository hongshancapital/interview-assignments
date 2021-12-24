import { Injectable, Scope } from '@nestjs/common';
// import { BloomFilter } from 'bloom-filters'
import { RedisService } from '../services/redis.service'

import BloomFilter from 'bloomfilter-redis'

@Injectable()
export class BloomFilterService {
    private bloomFilter: BloomFilter
    constructor(private readonly redisService: RedisService,) {
        // this.bloomFilter = bloomFilter
        // 暂时估算假设最多 10000 个 bit位， 100个url映射，基于公式 k = (m/n)loge2 ，算出hash函数数目约为 70
        this.bloomFilter = new BloomFilter({
            redisSize: 10000,
            hashesNum: 70,
            redisKey: 'Node_Bloomfilter_Redis',
            redisClient: this.redisService.client
        })
    }

    /**
     * 往布隆过滤器塞数据
     * @param {string} item
     * @memberof BloomFilterService
     */
    async addItemToBloomFilter(item: string) {
        await this.bloomFilter.add(item)
    }

    /**
     * 预判断当前项是否在DB中
     * @param {string} item
     * @returns
     * @memberof BloomFilterService
     */
    async hasItemInBloomFilter(item: string) {
        return this.bloomFilter.contains(item)
    }
}
