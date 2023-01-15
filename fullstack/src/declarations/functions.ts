/**
 * Miscellaneous shared functions go here.
 */

import {SnowflakeIdGenerate} from "./classes";

/**
 * Get a random number between 1 and 1,000,000,000,000
 */
export function getRandomInt(): number {
  return Math.floor(Math.random() * 1_000_000_000_000);
}

/**
 * Wait for a certain number of milliseconds.
 */
export function tick(milliseconds: number): Promise<void> {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve();
    }, milliseconds);
  });
}

/**
 * 获取雪花算法唯一ID生成的短链接
 * @returns 短链接
 */
export function generateShortUrl(): string {
  const idGenerate: SnowflakeIdGenerate = new SnowflakeIdGenerate();
  const char62 = "abcABCdefghDEFGHIJKLijklMNOPQRSmnTUVopqrstuvwxyzWXYZ0123456789";
  const radix = char62.length;
  // 需要做一下reverse，防止被猜测
  let decimalNum = Number(Array.from(String(idGenerate.generate())).reverse().join(""));
  const arr = [];
  do {
    const mod = decimalNum % radix;
    decimalNum = (decimalNum - mod) / radix;
    arr.unshift(char62[mod]);
  } while (decimalNum);
  return arr.join("").substring(0, 8);
}

/**
 * @description 检测原链接合法性
 * @param originalUrl 短链接地址
 * @returns boolean 类型
 */
export function isValidOriginalUrl(originalUrl: string): boolean {
  const exp = /^http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- ./?%&=]*)?/;
  const objExp = new RegExp(exp);
  return objExp.test(originalUrl);
}
/**
 * @description 检测短链接合法性
 * @param shortUrl 短链接地址
 * @returns boolean 类型
 */
export function isValidShortUrl(shortUrl: string): boolean {
  const exp = /^[a-zA-Z0-9]{1,8}$/;
  const objExp = new RegExp(exp);
  return objExp.test(shortUrl);
}
