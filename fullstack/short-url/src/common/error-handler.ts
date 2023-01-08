import { AppError, HttpCode } from './app-error';
import { Response } from 'express-serve-static-core';
import { logger } from '@/common/logging';

class ErrorHandler {
  private isTrustedError(error: Error): boolean {
    if (error instanceof AppError) {
      return error.isOperational;
    }

    return false;
  }

  private handleTrustedError(err: AppError, res: Response): void {
    res.status(err.httpCode);
    res.format({
      json: () => {
        res.json({ message: err.message });
      },
      html: () => {
        res.send(`<p>${err.message}</p>`);
      },
      default: () => {
        res.send(err.message);
      },
    });
  }

  private handleCriticalError(_: Error | AppError, res?: Response): void {
    if (res) {
      res.status(HttpCode.INTERNAL_SERVER_ERROR);
      res.format({
        json: () => {
          res.json({ message: 'Internal server error' });
        },
        html: () => {
          res.send('<p>Internal server error</p>');
        },
        default: () => {
          res.send('Internal server error');
        },
      });
    }

    logger.error('Application encountered a critical error. Exiting');
    process.exit(1);
  }

  public handleError(error: Error | AppError, res?: Response): void {
    if (this.isTrustedError(error) && res) {
      this.handleTrustedError(error as AppError, res);
    } else {
      this.handleCriticalError(error, res);
    }
  }
}

export const errorHandler = new ErrorHandler();
