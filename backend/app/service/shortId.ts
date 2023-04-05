/**
 * @file 短链接id生成方式
 * @author zengbaoqing<misterapptracy@gmail.com>
 */
import { Service } from 'egg';
import { customAlphabet } from 'nanoid/async';

export default class ShortIdService extends Service {

  public readonly charset = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ';

  public readonly maxIdLen = 8;

  public async getNewShortId() {
    return await customAlphabet(this.charset, this.maxIdLen)();
  }

}
