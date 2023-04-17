
import { NextFunction, Request, Response } from "express";
import { v4 as uuidV4 } from 'uuid';

export const TRACE_ID_HEADER = 'X-Trace-Id';
import { TRACE_ID, setupTraceId } from '../utils/logger';

export const asyncHook = async( req: Request, res: Response, next: NextFunction)   => {
  const traceId = req.header(TRACE_ID_HEADER)?.toString() || uuidV4();
  setupTraceId(traceId);
  Reflect.set(req, TRACE_ID, traceId);
  Reflect.set(res, TRACE_ID, traceId);
  res.setHeader(TRACE_ID_HEADER, traceId);

  return next();
};