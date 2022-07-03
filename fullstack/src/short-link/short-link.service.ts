import { Injectable, NotFoundException, OnModuleDestroy } from '@nestjs/common';
import {
  RedisService,
  DEFAULT_REDIS_NAMESPACE,
} from '@liaoliaots/nestjs-redis';
import { LinkEntity } from '../entities/link.entity';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import Redis from 'ioredis';
import { IdService } from '../id/id.service';

@Injectable()
export class ShortLinkService {
  private static IdCacheSeconds = 60 * 60;
  private static ReadDbLockSeconds = 1;
  private static ReadDbLockWaitMillSeconds = 100;
  private readonly redis: Redis;
  constructor(
    @InjectRepository(LinkEntity)
    private readonly linkRepository: Repository<LinkEntity>,
    private readonly redisService: RedisService,
    private readonly idService: IdService,
  ) {
    this.redis = this.redisService.getClient(DEFAULT_REDIS_NAMESPACE);
  }

  async findLinkById(id: string): Promise<LinkEntity> {
    const redisRes = await this.getRedisLink(id);
    if (redisRes && typeof redisRes === 'string') {
      return { id, link: redisRes, ctime: new Date() };
    }
    return this.findLinkInDb(id);
  }

  async findLinkInDb(id: string): Promise<LinkEntity> {
    // 使用redis做分布式锁
    const lockValue = this.idService.nextId();
    const redisLockRes = await this.getRedisLock(
      id,
      lockValue,
      ShortLinkService.ReadDbLockSeconds,
    );
    if (redisLockRes) {
      const linkEntity = await this.linkRepository.findOneBy({ id });
      if (linkEntity != null && typeof linkEntity === 'object') {
        await this.setRedisLink(
          id,
          linkEntity.link,
          ShortLinkService.IdCacheSeconds,
        );
        await this.delRedisLock(id, lockValue);
      }
      return linkEntity;
    } else {
      await this.wait(ShortLinkService.ReadDbLockWaitMillSeconds);
      const redisRes = await this.getRedisLink(id);
      if (redisRes && typeof redisRes === 'string') {
        return { id, link: redisRes, ctime: new Date() };
      } else {
        throw new NotFoundException();
      }
    }
  }

  async createLink(link: string, retryTimes: number): Promise<string> {
    const nextId = this.idService.nextId();
    return await this.insertLink(link, nextId, retryTimes);
  }

  async insertLink(
    link: string,
    id: string,
    retryTimes: number,
  ): Promise<string> {
    try {
      const res = await this.linkRepository.insert({ id, link });
      return res && res.identifiers && res.identifiers.length > 0 ? id : '';
    } catch (error) {
      const code = error.code;
      // error: duplicate key value, code: '23505'
      if (code === '23505' && retryTimes > 0) {
        return await this.createLink(link, retryTimes - 1);
      } else {
        console.log(
          'insertLink error: ',
          error,
          'retry times remain',
          retryTimes,
        );
        throw error;
      }
    }
  }

  async setRedisLink(
    id: string,
    link: string,
    seconds: number,
  ): Promise<string> {
    return this.redis.setex(this.getRedisIdKey(id), seconds, link);
  }

  async getRedisLink(id: string): Promise<string> {
    return this.redis.get(this.getRedisIdKey(id));
  }

  getRedisIdKey(id: string): string {
    return `id${id}`;
  }

  getRedisLockKey(id: string): string {
    return `idl${id}`;
  }

  async getRedisLock(
    id: string,
    lockValue: string,
    seconds: number,
  ): Promise<boolean> {
    const result = await this.redis.set(
      this.getRedisLockKey(id),
      lockValue,
      'EX',
      seconds,
      'NX',
    );

    // 上锁成功
    if (result === 'OK') {
      return true;
    } else {
      return false;
    }
  }

  async delRedisLock(id: string, lockValue: string): Promise<boolean> {
    const script =
      "if redis.call('get',KEYS[1]) == ARGV[1] then" +
      "   return redis.call('del',KEYS[1]) " +
      'else' +
      '   return 0 ' +
      'end';

    const result = await this.redis.eval(
      script,
      1,
      this.getRedisLockKey(id),
      lockValue,
    );
    if (result === 1) {
      return true;
    }
    return false;
  }

  wait(ms) {
    return new Promise((resolve) =>
      setTimeout(() => resolve(true), ms).unref(),
    );
  }
}
