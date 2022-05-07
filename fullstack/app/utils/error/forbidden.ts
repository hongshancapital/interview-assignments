import { BaseCustomError } from './base';

export class ForbiddenError extends BaseCustomError {
  get status() {
    return 403;
  }
}
