import { Response } from 'express';

// Http 重定向response的格式和状态
enum ResponseStatus {
  REDIRECT = 302,
}

abstract class ApiRedirectResponse {
  constructor(
    protected statusCode: ResponseStatus,
    protected url: string,
  ) { }

  public send(res: Response) {
    res.redirect(this.statusCode, this.url);
  }

}
export class FoundRedirectResponse extends ApiRedirectResponse {
  constructor(url : string) {
    super(ResponseStatus.REDIRECT,url);
  }
}
