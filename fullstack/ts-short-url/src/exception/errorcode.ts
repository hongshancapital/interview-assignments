import { Request, Response, NextFunction } from 'express';
import * as winston from 'winston';

export enum ServerCode {
    SU_SUCCES = 'SU_OK',                // 成功
    SU_REQ_ARG_ERROR = 'SU_REQ_ARG_ERROR',  // 请求参数异常
    SU_SERVER_EXCEPTION = 'SU_SERVER_EXCEPTION',     // 服务异常
    SU_CAN_NOT_FIND_URL = 'SU_CAN_NOT_FIND_URL',     // 查询源地址失败
    SU_CREATE_SHORTURL_FAIL = 'SU_CREATE_SHORTURL_FAILE'     // 服务端生成短链接失败
}

const logger = winston.createLogger({
  level: 'error',
  format: winston.format.json(),
  transports: [new winston.transports.File({ filename: './logs/error.log', level: 'error' })]
});

export function unCoughtErrorHandler(
  err: any,
  req: Request,
  res: Response,
  next: NextFunction,
) {
  const error: object = { Date: (new Date()).toLocaleString(), Reqbody: req.body, Stack: err };
  logger.error(error);
  res.end({"success":false, "code": ServerCode.SU_SERVER_EXCEPTION});
}

export function apiErrorHandler(
  err: any,
  req: Request,
  res: Response,
  message: string,
) {
  const error: object = { Date: (new Date()).toLocaleString(), Message: message, Reqbody: req.body, Stack: err };
  logger.error(error);
  res.json({"success":false, "code": ServerCode.SU_SERVER_EXCEPTION});
}
