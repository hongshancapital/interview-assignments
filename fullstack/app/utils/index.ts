import { IDateMap } from 'interface';
import { ParamValidateError } from './error';
import { TABLE, TIMESTAMP } from './constants';

/**
 * 左部位
 * @param {string} target 目标字符串
 * @param {string} filling 填充字符
 * @param {number} length 总长度
 * @return {string} str
 */
function leftPad(target: string, filling: string, length: number): string {
  const zero = new Array(length).join(filling);
  const str = zero + target;
  return str.slice(-length);
}

/**
 * 十进制转 62 进制
 * @param {number} value 十进制
 * @param {number} length 补位长度
 * @return {string} 62进制
 */
export function encode10To62(value: number, length = 8): string {
  if (value < 0) {
    throw new ParamValidateError('参数错误');
  }
  const chars = TABLE.split('');
  const radix = chars.length;
  let qutient = value;
  const array = [];
  do {
    const mod = qutient % radix;
    qutient = (qutient - mod) / radix;
    array.unshift(chars[mod]);
  } while (qutient);
  return leftPad(array.join(''), chars[0], length);
}

export function getDate(timestamp = TIMESTAMP): IDateMap {
  const now = Date.now();
  return {
    createDate: now,
    expireDate: now + timestamp,
  };
}
