import { Injectable } from '@nestjs/common';
import { RedisService } from './redis.service';
import { config } from '../../common/config';

@Injectable()
export class RedisShortService {
  constructor(private readonly redisService: RedisService) {}

  /**
   * 更具 code 获取 url ，可能返回为空，也就是不存在，防止大量不存在的 key 打到数据库
   * @param code
   * @returns
   */
  async get(code: string) {
    let url = await this.redisService.getClient().get(code);
    // 重新刷新过期时间
    await this.redisService
      .getClient()
      .expire(code, config.short.cache.redis.expire);
    return url;
  }

  async set(code: string, url?: string) {
    await this.redisService
      .getClient()
      .setex(code, config.short.cache.redis.expire, url);
  }

  async exists(code: string) {
    return await this.redisService.getClient().exists(code);
  }
}
