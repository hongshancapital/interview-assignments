import { createClient, createCluster, RedisClientType, RedisClusterType } from 'redis';

import config from "../config";
import { logger } from "../utils/logger";
import { container, singleton } from 'tsyringe';

@singleton()
export class RedisService {
  public client!: RedisClientType | RedisClusterType;
  private prefix = 'domain:';
  public ready = false;

  constructor() {
    this.init();
  }

  init() {
    try {
      if(config.isProduction()) {
        this.client = createCluster(config.get("redis.nodes"));
      } else {
        this.client = createClient(config.get("redis.node"));
      }
      this.client.on('error', (error: any)=> {
        logger.error('Redis error:', error);
        this.ready = false;
      });
      this.client.on('ready', () => {
        logger.info(`=================================`);
        logger.info(`🚀 Redis ready succeeded!`);
        logger.info(`=================================`);
        this.ready = true;
      });
      this.client.on('connect', () => {
        logger.info(`=================================`);
        logger.info(`🚀 Redis connect succeeded!`);
        logger.info(`=================================`);
        this.ready = true;
      });
      this.client.on('end', () => {
        logger.warn('Redis end!');
        this.ready = false;
      });
      return this.client;
    } catch(error) {
      logger.error(`Redis create error: ${error}`);
      this.ready = false;
    }
  }

  async connect () {
    if (this.ready) {
      return this.ready;
    }
    try {
      await this.client.connect();
      this.ready = true;
    } catch (error) {
      logger.error(`Redis connect error: ${error}`);
      this.ready = false;
    }
    return this.ready;
  }

  async disconnect () {
    if (!this.ready) {
      return !this.ready;
    }
    try {
      await this.client.disconnect();
      this.ready = false;
    } catch (error) {
      logger.error(`Redis disconnect error: ${error}`);
    }
    return !this.ready;
  }

  public getKey(key:string): string {
    return `${this.prefix}${key}`;
  }

  async createBloom(bloom: string, rate: number, capacity: number, options: any = {}): Promise<boolean> {
    const bloomName = this.getKey(bloom);
    if (!this.ready) {
      await this.connect();
    }
    try {
      const exists = await this.client.exists(bloomName);
      if (exists) {
        return true;
      }
      const result = await this.client.bf.reserve(bloomName,rate, capacity, options);
      return result === 'OK';
    } catch(error) {
      logger.error('Redis service bloom create error:', error);
      return false;
    }
  }

  async bloomAdd(bloom: string, key: string) {
    const bloomName = this.getKey(bloom);
    try {
      const exists = await this.client.bf.exists(bloomName, key);
      if (exists) {
        return exists;
      }
      return await this.client.bf.add(bloomName, key);
    } catch(error) {
      logger.error('Redis service bloom add error', error);
      return false;
    }
  }

  async bloomExists(bloom: string, key: string) {
    const bloomName = this.getKey(bloom);
    try {
      return await this.client.bf.exists(bloomName, key);
    } catch(error) {
      logger.error('Redis service bloom exists error', error);
    }
  }

  async setEx(key: string, value: string | number, duration = 60 * 60): Promise<string | null> {
    const originKey = this.getKey(key);
    try {
      return await this.client.set(originKey, value, {
        EX: duration
      });
    } catch(error) {
      logger.error('Redis service setEx error', error);
      return null;
    }
  }

  async get(key: string): Promise<string | null> {
    return await this.client.get(this.getKey(key));
  }

  getClient() {
    return this.client;
  }
}

const redisService = container.resolve(RedisService);
export default redisService;