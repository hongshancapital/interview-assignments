import { Request, Response, NextFunction } from 'express';
import * as winston from 'winston';

export enum ServerCode {
    SU_SUCCES = 'SU_OK',                // 成功
    SU_REQ_ARG_ERROR = 'SU_REQ_ARG_ERROR',  // 请求参数异常
    SU_SERVER_ERROR = 'SU_SERVER_ERROR',     // 服务异常
    SU_SERVER_CAN_NOT_FIND_URL = 'SU_SERVER_CAN_NOT_FIND_URL',     // 查询源地址失败
    SU_SERVER_CREATE_SHORTURL_FAIL = 'SU_SERVER_CREATE_SHORTURL_FAILE'     // 查询源地址失败
}


const file = new winston.transports.File({
  filename: '../logs/error.log',
  level: 'error',
  handleExceptions: true,
});

export function unCoughtErrorHandler(
  err: any,
  req: Request,
  res: Response,
  next: NextFunction,
) {
  winston.error(JSON.stringify(err));
  res.end({ error: err });
}

export function apiErrorHandler(
  err: any,
  req: Request,
  res: Response,
  message: string,
) {
  const error: object = { Message: message, Request: req, Stack: err };
  winston.error(JSON.stringify(error));
  res.json({ Message: message });
}
