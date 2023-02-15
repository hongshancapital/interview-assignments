import {
  ArgumentsHost,
  Catch,
  ExceptionFilter,
  HttpException,
} from '@nestjs/common';
import { Request, Response } from 'express';

@Catch()
export class ErrFilter implements ExceptionFilter {
  catch(exception: any, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const req = ctx.getRequest<Request>();
    const res = ctx.getResponse<Response>();

    if (exception instanceof HttpException) {
      let errMessage = exception.message;
      if (exception['response'] && exception['response'].message) {
        if (typeof exception['response'].message == 'string') {
          errMessage = exception['response'].message;
        } else if (exception['response'].message instanceof Array) {
          errMessage = exception['response'].message.join(',');
        }
      }
      res.status(200).json({
        statusCode: exception.getStatus(),
        message: errMessage,
      });
    } else {
      res.status(200).json({
        statusCode: 500,
        message: exception,
      });
    }
  }
}
