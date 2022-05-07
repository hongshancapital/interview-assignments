import { Context, Service } from 'egg';
import { ICacheClient, IRefreshCache, IUrlMapCache } from 'interface';
import { CACHE_PROCESS_KEY } from '../utils/constants';

export default class CacheService extends Service {
  client: ICacheClient;

  constructor(ctx: Context) {
    super(ctx);
    this.client = this.app.cache;
  }

  refreshAction(data: IRefreshCache) {
    // 向多进程发送消息
    this.app.messenger.sendToApp(CACHE_PROCESS_KEY, data);
  }

  async update(data: IRefreshCache) {
    switch (data.type) {
      case 'GET': {
        this.client.get(data.key as string);
        break;
      }
      case 'SET': {
        if (data.value) {
          this.client.set(data.key as string, data.value);
        }
        break;
      }
      case 'DEL': {
        this.client.del(data.key);
        break;
      }
      default:
        break;
    }
  }

  get(key: string): IUrlMapCache | null {
    const value = this.client.get(key) as IUrlMapCache | null;
    if (value) {
      this.refreshAction({
        pid: process.pid,
        type: 'GET',
        key,
      });
    }
    return this.client.get(key) as IUrlMapCache | null;
  }

  set(key: string, value: IUrlMapCache): void {
    this.refreshAction({
      pid: process.pid,
      type: 'SET',
      key,
      value,
    });
    return this.client.set(key, value);
  }

  del(key: string | string[]): number {
    const count = this.client.del(key);
    this.refreshAction({
      pid: process.pid,
      type: 'DEL',
      key,
    });
    return count;
  }
}
