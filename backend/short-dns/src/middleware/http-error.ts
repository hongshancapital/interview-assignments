import { HttpErrorStatusCode, errorMessages, HttpErrorCode } from '../types/errors';

export class HttpError extends Error {
  public status: HttpErrorStatusCode;
  public code: HttpErrorCode;

  constructor(status: HttpErrorStatusCode, errorCode: HttpErrorCode) {
    super(errorMessages[status]);
    this.code = errorCode;
    this.status = status;
  }

}
