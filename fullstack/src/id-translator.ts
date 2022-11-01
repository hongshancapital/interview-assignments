import Hashids from "hashids";

/**
 * 数字与字符串 id 转换器
 *
 * 采用 hashids 库，将数字 id 转换为字符串 id
 */
export class IdTranslator {
  private hashids: Hashids;

  constructor(salt: string = '') {
    this.hashids = new Hashids(salt, 5);
  }

  public encode(id: number): string {
    return this.hashids.encode(id)
  }

  public decode(hash: string): number | null {
    //hashids 不支持 bigint,但是签名却是 bigint|number,有点奇怪
    return this.hashids.decode(hash)[0] as (number | null)
  }
}
