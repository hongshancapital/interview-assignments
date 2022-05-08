import 'egg';
import { EggLogger } from 'egg';
declare module 'egg' {
  interface Application {
    redisClient: RedisClientType,
    cache: ICacheClient,
    bloomFilter: BloomFilter,
  }

}