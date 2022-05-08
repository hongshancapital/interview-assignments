import { Context, Service } from 'egg';
import { BloomFilter } from 'bloom-filters';
import { HashableInput } from 'bloom-filters/dist/utils';
import { BLOOM_PROCESS_KEY } from '../utils/constants';
import BloomFilterRedis from '../redis/bloom-filter';

// 导出数据
let exported: JSON;

export default class UrlMapService extends Service {
  filter: BloomFilter;
  bloomRedis: BloomFilterRedis;

  constructor(ctx: Context) {
    super(ctx);
    this.bloomRedis = this.app.redis.bloomFilter;
    this.filter = exported ? BloomFilter.fromJSON(exported) : this.app.bloomFilter;
  }

  async add(element: HashableInput) {
    this.filter.add(element);
    await this.refreshAction();
  }

  has(element: HashableInput): boolean {
    return this.filter.has(element);
  }

  update(data: JSON) {
    if (data) {
      exported = data;
      this.filter = BloomFilter.fromJSON(exported);
    }
  }

  async refreshAction() {
    const exported = this.filter.saveAsJSON();
    // redis 存储
    await this.bloomRedis.set(exported);
    // 向多进程发送消息
    this.app.messenger.sendToApp(BLOOM_PROCESS_KEY, {
      pid: process.pid,
      value: exported,
    });
  }
}
