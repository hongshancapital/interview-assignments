import {
  ArgumentsHost,
  Catch,
  ExceptionFilter,
  HttpException,
  HttpStatus,
  Logger,
} from '@nestjs/common';

@Catch(HttpException)
export class HttpExceptionFilter implements ExceptionFilter {
  constructor(private readonly logger: Logger) { }
  catch(exception: HttpException, host: ArgumentsHost) {
    const ctx = host.switchToHttp();
    const request = ctx.getRequest();
    const response = ctx.getResponse();

    const status =
      exception instanceof HttpException
        ? exception.getStatus()
        : HttpStatus.INTERNAL_SERVER_ERROR;

    const now = new Date().getTime();

    this.logger.error(
      `【${now}】${request.method} ${request.url} query:${JSON.stringify(request.query,)} params:${JSON.stringify(request.params)} body:${JSON.stringify(request.body)}`,
      JSON.stringify(exception.getResponse()),
      'HttpExceptionFilter');

    response.status(status);
    response.header('Content-Type', 'application/json;charset=utf-8');
    response.send(exception.getResponse());
  }
}
