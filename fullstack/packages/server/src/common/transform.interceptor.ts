import { Injectable, NestInterceptor, ExecutionContext, CallHandler, HttpStatus, HttpException } from '@nestjs/common';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { HttpMessage } from './http.result';

export interface Response<T> {
  data: T;
}

@Injectable()
export class TransformInterceptor<T> implements NestInterceptor<T, Response<T>> {
  intercept(context: ExecutionContext, next: CallHandler): Observable<Response<T>> {
    return next.handle().pipe(map(data => {
      const host = context.switchToHttp();
      const response = host.getResponse();
      const request = host.getRequest();
      let message = HttpMessage.SERVICE_UNAVAILABLE
      let statusCode = HttpStatus.SERVICE_UNAVAILABLE
      let page
      const { accept } = response.req.headers;
      /**
       * 页面渲染
       */
      if (/text\/html/g.test(accept)) {
        return data
      }

      // 获取失败
      if (!data) throw new HttpException({ statusCode: HttpStatus.NOT_FOUND, message: '内容不存在' }, HttpStatus.NOT_FOUND);
      if (typeof data === 'string') throw new HttpException(data, HttpStatus.FOUND);
      if (data.statusCode) throw new HttpException(data, data.statusCode);
      switch (request.method) {
        case 'GET':
          statusCode = HttpStatus.OK;
          message = '获取成功'; // 'GET ' + HttpMessage.OK;
          break;
        case 'POST':
          statusCode = HttpStatus.CREATED;
          message = '操作成功'; // 'POST ' + HttpMessage.CREATED;
          break;
        case 'PATCH':
          statusCode = HttpStatus.OK;
          message = '操作成功'; // 'PATCH ' + HttpMessage.OK;
          break;
        case 'PUT':
          statusCode = HttpStatus.OK;
          message = '操作成功'; // 'PUT ' + HttpMessage.OK;
          break;
        case 'DELETE':
          statusCode = HttpStatus.OK;
          message = '删除成功'; // 'DELETE ' + HttpMessage.OK;
          break;
        default:
          statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
          message = '请求出错'
          break;
      }
      /**
       * 获取数组
       */
      if (Array.isArray(data) && (data.length === 2) && (typeof data[0] === 'object') && (typeof data[1] === 'number')) {
        page = {
          count: data[1],
        }
        data = data[0]
      }

      throw new HttpException({
        statusCode,
        data,
        message,
        page,
        timestamp: new Date().toISOString()
      }, statusCode)
    }));
  }
}