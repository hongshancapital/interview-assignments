import { Provide, Inject } from '@midwayjs/decorator';
import { RedisService } from '@midwayjs/redis';

// https://github.com/RedisBloom/RedisBloom
const BfExistLuaScript = "return redis.call('bf.exists', KEYS[1], KEYS[2])";
const BfAddLuaScript = "return redis.call('bf.add', KEYS[1], KEYS[2])";
const BfKey = 'urlmap';

@Provide()
export class LocalRedisService {
  @Inject()
  redisService: RedisService;

  async get(key: string) {
    const res = await this.redisService.get(key);
    return res;
  }

  async set(key: string, value: string) {
    const res = await this.redisService.set(key, value);
    return res;
  }

  async bfAdd(key: string) {
    const res = await this.redisService.eval(BfAddLuaScript, 2, BfKey, key);
    return res;
  }

  async bfExist(key: string): Promise<boolean> {
    const res = await this.redisService.eval(BfExistLuaScript, 2, BfKey, key);
    return res === 1;
  }
}
