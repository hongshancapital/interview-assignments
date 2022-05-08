import { BaseCustomError, ErrorType } from './base';

export interface BadRequestErrorType extends ErrorType {
  [key: string]: any;
}

export class BadRequestError extends BaseCustomError<BadRequestErrorType> {
  get status() {
    return 400;
  }
}
