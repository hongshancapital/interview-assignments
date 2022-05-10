import { Injectable } from '@nestjs/common';
import { idTo62 } from './idTo62';

// 短域名最多8个字符(a-zA-Z0-9)也就是62^8=218340105584896组合约47个二进制位
// js最大安全正整数为2^53 - 1 = 9007199254740991, 也就是最多有53个二进制位可以分配
// 假设短域名时间为15年，15年开始时间会重置 时间域需要15 * 365 * 24 * 3600 * 1000 = 473040000000约2^39次方
// 假设生产id的QPS 3w 预估容量10W 10w/1000=100个/ms
// 假设worker的最大数量为2^4=16个 占用5个二进制位
// 假设同一worker同一毫秒最多产生的id数为 2^4约 16个 
// 每一秒同时可产生 16 * 16 * 1000 = 256个id

@Injectable()
export class IdfactoryService {

  // 时间域0所代表实际的开始时间
  private startTimeStamp = (new Date('2022-01-01 00:00:00 GMT')).getTime();

  // 当前工作的ID，每次服务启动需要从worker中心获取
  // 整个服务的生命周期不会变化
  private workerId: number = 0;

  // 当前的时间戳下的生成的序列号
  private currentIndex: number = 0;

  // 当前的时间（精确到毫秒）
  private currentTimestamp = 0;

  // 获取下个序列号
  private getNextIndex(ts: number): number {
    if (ts !== this.currentTimestamp) {
      this.currentTimestamp = ts;
      this.currentIndex = 0;
    } else if (this.currentIndex === 15) {
      throw new Error('duplicate key');
    }
    const index = this.currentIndex;
    this.currentIndex = (this.currentIndex + 1) % 16
    return index;
  }

  // 获取下一个全局ID
  public getNextId(): string {
    const ts = Date.now() - this.startTimeStamp;
    const nextIndex = this.getNextIndex(ts);
    const worker = this.workerId;
    const globalId = ts * 256 + worker * 16 + nextIndex;
    return idTo62(globalId);
  }
}
