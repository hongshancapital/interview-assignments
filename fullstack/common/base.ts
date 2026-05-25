import { DBProxy } from './db-proxy';

// 请求
export interface BLogicContext<T> {
  session: {
    permission: string;
    token: string;
    host: string;
    uid: string;
    username: string;
    appid: string;
  };
  input: T;
  params: any;
  proxy: DBProxy;
  originalUrl: string; // 请求路径
  reqNo: string; // 请求编号
}

// 返回
export interface ResultData {
  ok: boolean;
  data: object;
  err_code?: number;
  err_msg?: string;
}
