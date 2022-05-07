import { BaseCustomError, ErrorType } from './base';

export interface ParamValidateErrorType extends ErrorType {
  [key: string]: any;
}

export class ParamValidateError extends BaseCustomError<ParamValidateErrorType> {
  get status() {
    return 400;
  }
}
