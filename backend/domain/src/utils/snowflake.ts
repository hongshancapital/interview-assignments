export class Snowflake {
  private readonly epoch: bigint = 1672531200000n; // 2021-01-01T00:00:00Z
  private readonly workerIdBits: bigint = 6n;
  private readonly dataCenterIdBits: bigint = 6n;
  private readonly sequenceBits: bigint = 10n;

  private readonly maxWorkerId: bigint = (1n << this.workerIdBits) - 1n;
  private readonly maxDataCenterId: bigint = (1n << this.dataCenterIdBits) - 1n;
  private readonly maxSequence: bigint = (1n << this.sequenceBits) - 1n;

  private readonly workerIdShift: bigint = this.sequenceBits;
  private readonly dataCenterIdShift: bigint = this.sequenceBits + this.workerIdBits;
  private readonly timestampShift: bigint = this.sequenceBits + this.workerIdBits + this.dataCenterIdBits;

  private readonly workerId: bigint;
  private readonly dataCenterId: bigint;
  private sequence = 0n;
  private lastTimestamp = 0n;

  constructor(workerId: bigint, dataCenterId: bigint) {
    this.workerId = workerId % this.maxWorkerId;
    this.dataCenterId = dataCenterId % this.maxDataCenterId;
  }

  nextId(): bigint {
    let timestamp = BigInt(Date.now()) - this.epoch;

    if (timestamp < this.lastTimestamp) {
      throw new Error(`Clock moved backwards, refusing to generate ID for ${this.lastTimestamp - timestamp}ms`);
    }

    if (timestamp === this.lastTimestamp) {
      this.sequence = (this.sequence + 1n) & this.maxSequence;
      if (this.sequence === 0n) {
        timestamp = this.waitNextMillis(timestamp);
      }
    } else {
      this.sequence = 0n;
    }
    this.lastTimestamp = timestamp;

    return (timestamp << this.timestampShift) |
      (this.dataCenterId << this.dataCenterIdShift) |
      (this.workerId << this.workerIdShift) |
      this.sequence;
  }

  private waitNextMillis(timestamp: bigint): bigint {
    while (timestamp <= this.lastTimestamp) {
      timestamp = BigInt(Date.now()) - this.epoch;
    }
    return timestamp;
  }
}
