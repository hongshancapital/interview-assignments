// js最大内置支持的基数
const Const_Radix = 36;
// 短网址参数位数
const Const_Short_Url_Length = 8;
// 在内置基数下, 所能支持的最大表达值, 当前状态下最多记录 2821109907455 条数据
// 若数据超过Number.MAX_SAFE_INTEGER, 需考虑数字准确性问题.
// 目前离阈值仍有3个数量级, 因此可以先不考虑
const Const_Max_Size = parseInt(
  "z".padEnd(Const_Short_Url_Length, "z"),
  Const_Radix
);

const Const_Base = Math.pow(10, `${Const_Max_Size}`.length + 1);

/**
 * key-value数据库, 用于存储.
 */
export class Storage {
  // 将url按shortUrlKey => url的方式存储
  private static db: { [key: string]: string } = {};
  // 按url => shortUrlKey的方式存储, 便于判断url是否已经生成过短网址
  private static reverseDb: { [key: string]: string } = {};

  /**
   * 生成一个随机且不在db中存在的key
   *
   * 也可以用随机排序的字符串作为映射字符, 但只要最终id是递增的, 就总会被人找到规律, 反而没有安全性. 不如随机数法, 在样本量小于1000亿时有良好的性能
   */
  private static generateKey() {
    // 碰撞后需要重新生成一个key, 当数据量达到1000亿时, 连续碰撞5次的概率也只有(1/10)^5, 不足万分之一.
    let isExist = true;
    let randomKeyStr = "";
    while (isExist) {
      // 生成随机数
      let randomKey = Math.trunc(Math.random() * Const_Base) % Const_Max_Size;
      // 通过36进制转换为字符串
      randomKeyStr = randomKey.toString(Const_Radix);
      isExist = this.db[randomKey] !== undefined;
    }
    // 空缺位数补0
    randomKeyStr = randomKeyStr.padStart(Const_Short_Url_Length, "0");
    return randomKeyStr;
  }

  /**
   * 根据key, 从数据库中获取对应url, 没有则返回undefined
   * @param key
   * @returns
   */
  static get(key: string): string | undefined {
    return this.db[key];
  }

  /**
   * 添加url记录
   * @param value
   * @returns
   */
  static add(value: string): string {
    if (this.reverseDb[value] !== undefined) {
      // 已经存储过, 直接返回原记录即可
      return this.reverseDb[value];
    }

    let key = this.generateKey();
    this.db[key] = value;
    this.reverseDb[value] = key; // 额外存一份反向链接, 方便检测
    return key;
  }
}
