import {
  ExceptionFilter, Catch, ArgumentsHost, HttpException, HttpStatus
} from '@nestjs/common';
import { Request, Response } from 'express';

@Catch(HttpException)
export class HttpExceptionFilter implements ExceptionFilter {
  catch(exception: HttpException, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const response = ctx.getResponse<Response>();
    const request = ctx.getRequest<Request>();
    const status = exception.getStatus();

    const statusCode = exception instanceof HttpException ? exception.getStatus()
      : HttpStatus.INTERNAL_SERVER_ERROR;
    let result
    console.log('exception', exception);
    if (exception instanceof HttpException) {
      const excep = exception.getResponse()

      console.log('excep', excep);
      if (typeof excep != 'string') {
        result = {
          ...result,
          ...excep
        }

        response.status(result.statusCode || statusCode).json(result);
      } else {
        response.setHeader('Content-Type', 'application/xml');
        response.send(excep);
      }
    } else {
      result = {
        ...result,
        ...(typeof exception == 'object' ? exception : {}),
        timestamp: new Date().toISOString(),
      }
      response.status(result.statusCode || statusCode).json(result);
    }
  }
}