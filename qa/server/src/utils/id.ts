import murmurhash from "murmurhash";

export class ShortId {
  // 起始时间 2022-05-31 23:01
  private beginMinute = 1654009318;

  private timeBits = 25;
  private workBits = 6;
  private hashBits = 32;

  private maxWorkNum = Math.pow(2, this.workBits);

  private workId: number;

  // 8 bit mask
  private max8bitNum = Math.pow(2, 8) - 1;

  constructor(workId: number) {
    if (workId > this.maxWorkNum) {
      throw new Error("workId max number overflow");
    }

    this.workId = workId;
  }

  /**
   * 生成 shortId
   * 详细  |1 bit 符号位 | 25 bit 时间小时 | 6 bit 机器位 |  32 bit hash |
   *
   * @param lurl 长地址
   * @returns
   */
  public generateShortId = (lurl: string): string => {
    const arr: number[] = [];
    for (let i = 0, j = lurl.length; i < j; i++) {
      arr.push(lurl.charCodeAt(i));
    }

    // 前32bit
    const first32bitNum =
      (Math.round((new Date().getTime() / 1000 - this.beginMinute) / 60) <<
        this.timeBits) |
      this.workId;

    // 后32bit
    const second32BitNum = murmurhash.v3(new Uint8Array(arr));

    // 备注： 之所以拆分两个，是因为 js 的 整型部分最大不能到 64 位（Java 是可以的）

    return `${this.to62Hex(first32bitNum)}${this.to62Hex(second32BitNum)}`;
  };

  /**
   * 利用数字生成生成 0-9  a-z  A-Z 的 62 个字符（进制转化思想）
   * @param num 随机数字
   * @returns
   */
  public to62Hex = (num: number): string => {
    num = Math.abs(num);
    const chars =
      "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    const arr = [];

    let index = 0;
    do {
      index = num & this.max8bitNum;
      arr.push(chars.charAt(index % 62));
      num = num >> 8;
    } while (num != 0);

    return arr.join("");
  };
}
