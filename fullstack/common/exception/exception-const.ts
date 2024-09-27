export enum ErrorConst {
  AUTH_LOGIN = 999,
  SQL_ERROR = 1001,
  External_API = 1002,
  WX_AUTH_TOKEN = 4001,
  WX_CREATE_MENU = 4002,
}

export enum ErrorMessage {
  AUTH_LOGIN = "登录验证失败",
  SQL_ERROR = "数据库操作失败",
}

/**
 * 返回错误信息
 * @param errCode：number 错误码
 * @param msg：string[] 错误提示
 * @returns 字符串可能是内部错误，直接返回
 * @returns 数组或者不存在，用定义好的错误提示和msg拼接
 * @returns 其余情况（对象）序列化后返回
 */
export function getErrorMessage(errCode: number, msg?: string[]) {
  if (typeof msg === "string") {
    return msg;
  } else if (Array.isArray(msg) || !msg) {
    let code = ErrorConst[errCode];
    let info: any = ErrorMessage[`${code as keyof typeof ErrorMessage}`];
    if (info && msg && msg.length == 1) {
      info = `${msg[0]}${info}`;
    }
    if (info && msg && msg.length > 1) {
      info = `${msg[0]}${info}${msg[1]}`;
    }
    if (!info && msg && msg.length) {
      info = msg[0];
    }
    return info;
  } else {
    return JSON.stringify(msg);
  }
}
