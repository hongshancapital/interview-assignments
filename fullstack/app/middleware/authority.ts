import { Context, EggAppConfig } from 'egg';

export default function visitorMiddleware(_appInfo: EggAppConfig): any {
  return async (_ctx: Context, next: any) => {
    // TODO 权限校验
    await next();
  };
}
