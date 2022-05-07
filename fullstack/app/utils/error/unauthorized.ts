import { BaseCustomError } from './base';

export class UnauthorizedError extends BaseCustomError {
  get status() {
    return 401;
  }
}
