import { getErrorMessage } from "./exception-const";

export class SystemException extends Error {
  code: number;
  msg: string;
  err: Error | undefined;
  constructor(errCode: number, errMsg?: string[], err?: Error) {
    let msg: string = getErrorMessage(errCode, errMsg);
    super(msg);
    this.code = errCode;
    this.msg = msg;
    if (err) {
      this.err = err;
    }
  }
}
