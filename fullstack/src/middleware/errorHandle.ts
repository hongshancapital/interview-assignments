import { Response, Request, NextFunction } from 'express';
import { ApiResult, ResponseStatus } from "../types/apiResult";
import logger from '../utils/logger';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
export default function (err: Error, req: Request, res: Response, next: NextFunction): void {
  logger.error(`接口错误${req.path}`, err)
  const result = new ApiResult(ResponseStatus.fail, null, err.message)
  res.json(result)
}