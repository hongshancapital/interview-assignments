/**
 * 随机数生成
 */
import { config } from './config';

export const randomStr = (alphabet: string, len: number) => {
  let result = '';
  while (len > 0) {
    //循环次数是指定长度
    len--;
    // 0 <= Math.floor(Math.random() * (alphabet.length )) < alphabet.length
    result += alphabet[Math.floor(Math.random() * alphabet.length)];
  }
  return result;
};

export const randomShortCode = () => {
  return randomStr(config.short.alphabet, config.short.len);
};
