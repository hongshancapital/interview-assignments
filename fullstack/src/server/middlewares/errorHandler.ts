import { NextFunction, Request, Response } from "express-serve-static-core";
import {AppError, InternalError } from '../utils/appError';
import { logger } from "../utils/logger";

/**
 * a middleware for error handling
*/
export const errorHandler = (
  error: Error,
  _req: Request,
  res: Response,
  _next: NextFunction
): void => {
  if (error instanceof AppError) {
    AppError.handle(error, res);
    return;
  }
  logger.error('App_Error:', error);
  AppError.handle(new InternalError(error.message), res);
  return;
};