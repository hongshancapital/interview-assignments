import {Context} from 'egg';

/**
 * 统一异常处理
 */
export default function Exception(): any {
  return async (ctx: Context, next: () => Promise<any>) => {
    try {
      await next();
    } catch (err) {
      const {errors} = err;
      ctx.logger.error('[Exception]', err.message, errors);
      ctx.set('Content-Type', 'application/json');
      // 生产环境时 500 错误的详细错误内容不返回给客户端，因为可能包含敏感信息
      const status = err.status || 500;
      const message = status === 500 && ctx.app.config.env === 'prod' ? '服务器好像出了点问题...稍后再试试' : err.message;
      ctx.status = status;
      ctx.body = JSON.stringify({
        errorCode: err.errorCode || 500,
        message,
      });
    }
  };
}
