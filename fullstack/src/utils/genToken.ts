/*
 * @Author: zhangyan
 * @Date: 2023-03-09 13:50:37
 * @LastEditTime: 2023-03-12 18:09:03
 * @LastEditors: zhangyan
 * @FilePath: /fullstack/src/utils/genToken.ts
 * @Description: token生成方式，这里主要采用base62(时间戳+ hrtime + 随机数 + 自增序列)编码生成，
 * 这里自增id用变量模拟，通过自增序列的唯一性来避免碰撞
 *
 * 也可以使用nanoid，但是也不是绝对的不会碰撞
 *
 * Hash或者MD5如果是限制8位token 碰撞率较高
 * 
 * 保证不碰撞的前提就是需要有一个唯一的key，一般采用数据库自增id，
 * 如果要适应高并发，需要有高效发号器设计，分库分表+锁，采用布隆过滤器等提高性能
 * 比如10个发号器，第一个发号器，发0-100，第二个发101-200，以此类推
 */
import base62 from "base62";
let count: number = 1;

export function genToken() {
  let timeStamp: number = new Date().getTime();
  let random: number = Math.ceil(Math.random() * 100000);
  let hrTime = process.hrtime();
  // 时间戳 + hrtime + 随机数 + 自增序列
  let token: string = base62.encode(timeStamp + hrTime[1] + random + count++);
  // 超过8位截取后8位
  if (token.length > 8) {
    token = token.substring(token.length - 8, token.length);
  }
  return token;
}
