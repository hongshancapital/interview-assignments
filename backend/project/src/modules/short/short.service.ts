import { HttpException, HttpStatus, Injectable } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { randomShortCode } from '../../common/util';
import { ShortEntity } from '../../entities/short.entity';
import { Repository } from 'typeorm';
import { CacheShortService } from '../cache/cache.short.service';
import { RedisShortService } from '../redis/redis.short.service';
import { config } from '../../common/config';
@Injectable()
export class ShortService {
  constructor(
    @InjectRepository(ShortEntity)
    private readonly shortEntity: Repository<ShortEntity>,
    private readonly redisShortService: RedisShortService,
    private readonly cacheShortService: CacheShortService,
  ) {}

  async upload(url: string) {
    let code = await this.generateShortCode();
    await this.shortEntity.insert({
      code,
      url,
    });
    // 做缓存
    await this.redisShortService.set(code, url);
    this.cacheShortService.put(code, url);
    return this.generateShortUrlByCode(code);
  }

  async generateShortUrlByCode(code: string) {
    let url = new URL(config.domain.join(':'));
    url.pathname = '/short/' + code;
    return url.toString();
  }
  /**
   * 生成短码
   * @returns
   */
  async generateShortCode() {
    let code = randomShortCode();
    let exist = await this.codeExist(code);
    let retry = config.short.retry;
    while (exist && retry) {
      retry--;
      code = randomShortCode();
      exist = await this.codeExist(code);
    }
    if (exist) throw '生成失败';
    return code;
  }

  /**
   * 三级缓存判断 code 是否存在
   * @param code
   * @returns
   */
  async codeExist(code: string) {
    if (this.cacheShortService.get(code)) return true;
    if (await this.redisShortService.get(code)) return true;
    return await this.shortEntity.exist({ where: { code } });
  }

  /**
   * 查询
   * @param code
   * @returns
   */
  async getUrlByCode(code: string) {
    let url = this.cacheShortService.get(code);
    // 判断 code 在不在 redis
    let exist = await this.redisShortService.exists(code);
    // redis 查询
    if (exist && !url) url = await this.redisShortService.get(code);
    // 没有 url 且 不在 redis 就去数据库查询
    if (!url && !exist) {
      let short = await this.shortEntity.findOne({ where: { code } });
      if (short) url = short.url;
    }
    // 内存缓存存在的 code
    if (url) this.cacheShortService.put(code, url);
    // 保存到 redis , url 可能是空
    await this.redisShortService.set(code, url);
    if (!url) throw new HttpException('未查询到信息', HttpStatus.BAD_REQUEST);
    return url;
  }
}
