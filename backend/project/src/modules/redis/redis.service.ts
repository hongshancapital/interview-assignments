import { Injectable } from '@nestjs/common';
import Redis from 'ioredis';
import { config } from '../../common/config';

@Injectable()
export class RedisService {
  private client: Redis;

  constructor() {
    this.client = new Redis(config.redis);
  }

  getClient() {
    return this.client;
  }
}
