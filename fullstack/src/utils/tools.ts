/*
 * @Author: Json Chen
 * @Date: 2023-02-13 16:55:30
 * @LastEditors: Json Chen
 * @LastEditTime: 2023-02-13 18:13:31
 * @FilePath: /interview-assignments/fullstack/src/utils/tools.ts
 */
import { v4 as uuidv4 } from 'uuid';
export const generateShortId = () => {
  return uuidv4().substring(0, 8);
};