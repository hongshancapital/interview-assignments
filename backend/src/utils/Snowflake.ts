import { convert10to62 } from "./RadixUtil";

class Snowflake {
  private twepoch: bigint
  private sequenceBits: bigint
  private timestampLeftShift: bigint
  private sequenceMask: bigint
  public lastTimestamp: bigint
  public sequence: bigint

  constructor() {

    /** 开始时间截 ：2023-03-08 16:49:23 */
    this.twepoch = 1678454599724n;

    /** 序列在id中占的位数 */
    this.sequenceBits = 8n;

    this.timestampLeftShift = this.sequenceBits;
    this.sequenceMask = -1n ^ (-1n << this.sequenceBits);

    /** 上次生成ID的时间截 */
    this.lastTimestamp = -1n;
    this.sequence = 0n;
  }

  public nextId() {
    var timestamp = this.timeGen();

    //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
    if (timestamp < this.lastTimestamp) {
      throw new Error('Clock moved backwards. Refusing to generate id for ' +
        (this.lastTimestamp - timestamp));
    }

    //如果是同一时间生成的，则进行毫秒内序列
    if (this.lastTimestamp === timestamp) {
      this.sequence = (this.sequence + 1n) & this.sequenceMask;
      //毫秒内序列溢出
      if (this.sequence === 0n) {
        //阻塞到下一个毫秒,获得新的时间戳
        timestamp = this.tilNextMillis(this.lastTimestamp);
      }
    } else {
      //时间戳改变，毫秒内序列重置
      this.sequence = 0n;
    }

    //上次生成ID的时间截
    this.lastTimestamp = timestamp;

    //移位并通过或运算拼到一起组成64位的ID
    let result = ((timestamp - this.twepoch) << this.timestampLeftShift) | this.sequence
    return result;
  };

  /**
   * 阻塞到下一个毫秒，直到获得新的时间戳
   * @param lastTimestamp 上次生成ID的时间截
   * @return 当前时间戳
   */
  private tilNextMillis(lastTimestamp: bigint) {
    var timestamp = this.timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = this.timeGen();
    }
    return timestamp;
  };

  /**
   * 返回以毫秒为单位的当前时间
   * @return 当前时间(毫秒)
   */
  private timeGen() {
    return BigInt(Date.now());
  };

  public snowflakeGenerator() {
    return convert10to62(this.nextId())
  }
}

const snowflake = new Snowflake();

export default snowflake;