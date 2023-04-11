import { Injectable } from '@nestjs/common';
import * as crc32 from 'crc32';

const chars = [
  '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
  'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
  'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
];

@Injectable()
export class SharedService {

  /**
   * 计算字符串的CRC32哈希值
   * @param str 要计算CRC32的字符串
   * @returns 计算后的CRC32哈希值
   */
  crc32(str: string): number {
    if (str == '') {
      return 0;
    }
    const hex = crc32(str);
    return Number.parseInt(hex, 16);
  }

  /**
   * 将数字num转换为62进制
   * @param num 要转换为62进制的数字
   */
  to62Str(num: number): string {
    if (num < 0) {
      throw new Error('num必须大于等于0');
    }
    const result = [];
    while (num > 0) {
      const mod = num % 62;
      result.push(chars[mod]);
      num = Math.floor(num / 62);
    }
    return result.reverse().join('');
  }

  /**
   * 将62进制字符串转换为Number
   * @param str 62进制字符串
   */
  strToNum(str: string): number {
    let num = 0;
    for (let i = 0; i < str.length; i++) {
      const ch = str[i];
      if (!chars.includes(ch)) {
        throw new Error('str格式错误！');
      }
      const n = chars.indexOf(ch);
      num += n * Math.pow(62, str.length - 1 - i);
    }
    return num;
  }
}
