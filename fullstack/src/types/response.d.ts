/*
 * @Author: zhangyan
 * @Date: 2023-03-09 02:56:34
 * @LastEditTime: 2023-03-12 17:37:23
 * @LastEditors: zhangyan
 * @FilePath: /fullstack/src/types/response.d.ts
 * @Description: 给Response增加两个封装方法，分别返回成功和失败的json
 */

import { ErrorType } from "../types/errorCode";
export * from "express-serve-static-core";
declare module "express-serve-static-core" {
  interface Response {
    success: (result: any) => void;
    fail: (type: ErrorType, message?: string, result?: any) => void;
  }
}
