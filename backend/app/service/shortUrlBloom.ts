/**
 * @file 简易布隆过滤器
 * @author zengbaoqing<misterapptracy@gmail.com>
 * @desc 过滤器的设计主要有两方面的作用：1.对新生成的短连接做碰撞识别 2.用于缓存穿透防护作用
 * @desc 当前过滤器只是简单设计，当数据量过于庞大时，还需要借助bloom过滤器的算法来实现
 * @desc TODO: 过滤器的作用尤为重要，需要考虑redis宕机后的1s（采用aof，可以保证至多丢失1s数据）之内的数据恢复（可以将宕机时间前1min的数据同步到redis）
 */
import { Service } from 'egg';

export default class ShortUrlBloomService extends Service {

  private readonly urlSetKey = 'short:urls:set';

  public async contains(shortId: string) {
    return !!(await this.app.redis.sismember(this.urlSetKey, shortId));
  }

  public async add(shortId: string) {
    await this.app.redis.sadd(this.urlSetKey, shortId);
  }

  // public async clear() {
  //   await this.app.redis.del(this.urlSetKey);
  // }

}
