import { Response } from 'express';
import { url } from 'inspector';

// Helper code for the API consumer to understand the error and handle is accordingly
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
