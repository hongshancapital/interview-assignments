export const HostDomain: string = "http://xxx.com/";

class MyUtil {
  private charStr: string;
  private charLen: number;
  constructor() {
    this.charStr =
      "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    this.charLen = this.charStr.length;
  }
  public validateUrl(str: string): boolean {
    const reg =
      /^(?:(http|https|ftp):\/\/)?((?:[\w-]+\.)+[a-z0-9]+)((?:\/[^/?#]*)+)?(\?[^#]+)?(#.+)?$/i;
    return reg.test(str);
  }

  public numToBase62String(num: number): string {
    var base62Str: string = "";
    while (num > 0) {
      var index: number = num % this.charLen;
      base62Str = this.charStr.charAt(index) + base62Str;
      num = Math.floor(num / this.charLen);
    }
    return base62Str;
  }

  public base62StringToNumber(str: string): number {
    const strLen: number = str.length;
    let multiplier: number = 1;
    let result: number = 0;
    for (let i = strLen - 1; i >= 0; i--) {
      const index: number = this.charStr.indexOf(str[i]);
      result += index * multiplier;
      multiplier *= this.charLen;
    }
    return result;
  }

  public genShortUrl(count: number): string {
    count = Math.ceil(count);
    return HostDomain + this.numToBase62String(count);
  }
}

export const myUtil = new MyUtil();
