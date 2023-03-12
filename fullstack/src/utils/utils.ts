/*
 * @Author: zhangyan
 * @Date: 2023-03-11 00:38:35
 * @LastEditTime: 2023-03-11 00:41:11
 * @LastEditors: zhangyan
 * @FilePath: /fullstack/src/utils/utils.ts
 * @Description: 检查是否是合法的url
 */

export function isValidHttpUrl(url :string) {
    try {
      const newUrl = new URL(url);
      return newUrl.protocol === 'http:' || newUrl.protocol === 'https:';
    } catch (err) {
      return false;
    }
  }