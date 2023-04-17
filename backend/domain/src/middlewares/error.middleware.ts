import { NextFunction, Request, Response } from "express";
import { ApplicationError } from "../helpers/application.err";
import { logger, TRACE_ID } from "../utils/logger";

export const errorHandler = async(error: ApplicationError, req: Request, res: Response, next: NextFunction ) => {
  const status: number = error.status || 500;
  const message: string = error.message || "Something went wrong";
  const traceId = Reflect.get(req, TRACE_ID);
  logger.error(
    `[${traceId}]: [${req.method}] ${req.path} -- status:: ${status}, message:: ${message}, error:: ${error}`
  );
  res.status(status).json({ status, message });
};
