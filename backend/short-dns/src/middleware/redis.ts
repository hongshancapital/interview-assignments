import { createClient, RedisClientType } from 'redis';
import { REDIS_URL } from '../config/env';

export class RedisUtils {
  private client: RedisClientType;

  constructor() {
    this.client = createClient({
      url: REDIS_URL,
    });
    // eslint-disable-next-line @typescript-eslint/no-floating-promises
    this.client.connect();
  }

  public get = async (key: string): Promise<string> => {
    const value = await this.client.get(key);
    if (!value) return '';
    return value;
  };

  public set = async (key: string, value: string, EX: number): Promise<boolean> => {
    return await this.client.set(key, value, { NX: true, EX }) === 'OK';
  };

}
