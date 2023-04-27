import { StatusCode } from "../model/status_code";

export class PageInfo {
  //status code
  statusCode: StatusCode;

  //result
  result: string;

  //message
  message: string;

  constructor(sc: StatusCode, r: string, m: string) {
    this.statusCode = sc;
    this.result = r;
    this.message = m;
  }
}
