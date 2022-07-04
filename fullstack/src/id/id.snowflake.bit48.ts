/**
 * 自定义雪花算法，2进制48位长度，可转换成64进制下8位长度，从高位到地位：4字节工作id+41字节毫米时间戳+3字节序列号
 */

export class SnowflakeIdBit48 {
  // 服务id占多少个bit位
  private workerIdBits: number;
  // 服务id偏移位
  private workerIdShift: number;
  // 服务id
  private workerId: number;
  // 最大服务id
  private maxWorkerId: number;
  // 流水号占多少个bit位
  private sequenceBits: number;
  // 流水号偏移位
  private sequenceShift: number;
  // 流水号
  private sequence: number;
  // 最大流水号
  private maxSequence: number;
  // 毫秒时间戳占多少个bit位
  private timestampBits: number;
  // 毫秒时间戳偏移位
  private timestampShift: number;
  // 时间戳
  private lastTimestamp: bigint;
  constructor({ workerId }) {
    this.sequenceShift = Number(0);
    this.sequenceBits = Number(3);
    this.maxSequence = Number(-1 ^ (-1 << this.sequenceBits)); // 7
    this.timestampBits = Number(41);
    this.timestampShift = this.sequenceShift + this.sequenceBits; // 3
    this.workerIdShift = this.timestampShift + this.timestampBits; // 44
    this.workerIdBits = Number(4);
    this.maxWorkerId = Number(1 << this.workerIdBits);
    this.workerId =
      typeof workerId === 'number' && workerId < this.maxWorkerId
        ? workerId
        : undefined;
    this.lastTimestamp = BigInt(0);
    this.sequence = Number(0);
  }

  private tilNextMills(lastTimestamp: bigint): bigint {
    let timestamp = this.timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = this.timeGen();
    }
    return BigInt(timestamp);
  }

  private timeGen(): bigint {
    return BigInt(Date.now());
  }

  public nextBigId(): bigint {
    let timestamp = this.timeGen();
    if (this.lastTimestamp === timestamp) {
      this.sequence = (this.sequence + 1) & this.maxSequence;
      if (this.sequence === 0) {
        timestamp = this.tilNextMills(this.lastTimestamp);
      }
    } else {
      this.sequence = 0;
    }
    this.lastTimestamp = timestamp;

    const workerId =
      typeof this.workerId === 'number'
        ? this.workerId
        : Math.floor(Math.random() * this.maxWorkerId);
    const res =
      (BigInt(workerId) << BigInt(this.workerIdShift)) |
      (timestamp << BigInt(this.timestampShift)) |
      (BigInt(this.sequence) << BigInt(this.sequenceShift));
    return res;
  }
}
