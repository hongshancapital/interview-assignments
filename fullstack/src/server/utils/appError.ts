import { Response } from "express-serve-static-core";

import {
  NotFoundResponse,
  InternalErrorResponse,
} from './appResponse';

enum ErrorType {
  INTERNAL = 'InternalError',
  NOT_FOUND = 'NotFoundError',
}

export abstract class AppError extends Error {
  constructor(public type: ErrorType, public message: string = 'error') {
    super(type);
  }

  public static handle(err: AppError, res: Response): void {
    if (err.type === ErrorType.NOT_FOUND) {
      new NotFoundResponse(res).send();
      return;
    }

    const message = err.message;
    new InternalErrorResponse(res, message).send();
  }
}

export class InternalError extends AppError {
  constructor(message = 'Internal error') {
    super(ErrorType.INTERNAL, message);
  }
}

export class NotFoundError extends AppError {
  constructor(message = 'Not Found') {
    super(ErrorType.NOT_FOUND, message);
  }
}
