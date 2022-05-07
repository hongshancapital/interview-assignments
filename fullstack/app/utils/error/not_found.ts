import { BaseCustomError } from './base';

export class NotFoundError extends BaseCustomError {
  get status() {
    return 404;
  }
}
