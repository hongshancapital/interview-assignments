/*
 * @Author: zhangyan
 * @Date: 2023-03-09 02:56:10
 * @LastEditTime: 2023-03-12 18:37:10
 * @LastEditors: zhangyan
 * @FilePath: /fullstack/src/types/errorCode.ts
 * @Description: 全局统一错误信息
 */

interface ErrorInfo {
  msg: string;
  code: number;
}

// #cycode_secret_ignore_here
export enum ErrorType {
  succ = 0,
  server_error = 500,
  miss_param = 10000,
  url_exists = 10001,
  incorrect_token = 10002,
  incorrect_url = 10003,
  add_url_faild = 10004,
}
// #cycode_secret_ignore_here

export const errorCode: { [key: number]: ErrorInfo } = {
  [ErrorType.succ]: { msg: "Success", code: ErrorType.succ },
  [ErrorType.server_error]: {
    msg: "Server Error",
    code: ErrorType.server_error,
  },
  [ErrorType.miss_param]: {
    msg: "Missing parameters",
    code: ErrorType.miss_param,
  },
  [ErrorType.url_exists]: {
    msg: "Url already exists",
    code: ErrorType.url_exists,
  },
  [ErrorType.incorrect_token]: {
    msg: "Incorrect token",
    code: ErrorType.incorrect_token,
  },
  [ErrorType.incorrect_url]: {
    msg: "Incorrect url format",
    code: ErrorType.incorrect_url,
  },
  [ErrorType.add_url_faild]: {
    msg: "Add url failed, please try again later",
    code: ErrorType.add_url_faild,
  },
};
