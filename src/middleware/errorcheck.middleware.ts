import { IMiddleware } from '@midwayjs/core';
import { Middleware } from '@midwayjs/decorator';
import { NextFunction, Context } from '@midwayjs/koa';

@Middleware()
export class ErrorCheckMiddleware
  implements IMiddleware<Context, NextFunction>
{
  resolve() {
    return async (ctx: Context, next: NextFunction) => {
      try {
        const result = await next();
        return result;
      } catch (e) {
        console.error(e);
        return {
          code: -1,
          msg: 'system_error',
        };
      }
    };
  }

  static getName(): string {
    return 'errorcheck';
  }
}
