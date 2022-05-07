import { Context } from 'egg';
import { IErrorResponse } from 'web-api';

export default function errorHandlerMiddleware() {
  return async (ctx: Context, next: any) => {
    try {
      await next();
    } catch (error: any) {
      const response: IErrorResponse = {
        success: false,
        error: {
          code: error.code,
          message: error.message,
        },
      };
      if (response.error && response.error.code === -1) {
        response.error.code = error.status;
      }
      ctx.body = response;
    }
  };
}
