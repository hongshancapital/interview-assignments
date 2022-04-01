import {Application, Context} from 'egg';

/**
 * 记录各个routerUrl的路由配置，使用initRouter统一设置
 */
const __router__: any = {};

/**
 * egg suport http method
 */
export type HttpMethod = 'get' | 'post' | 'patch' | 'delete' | 'options' | 'put' | 'all';

/**
 * RouterOption
 */
interface RouterOption {
  httpMethod: HttpMethod;
  handleName: string;
  beforeMiddlewares: any[];
  constructorFn: any;
  className: string;
  validator?: Validator | undefined | null;
  url: string;
}

interface Validator {
  params?: any;
  body?: any;
}

/**
 * 推入路由配置
 */
function _setRouter(url: string, option: RouterOption) {
  __router__[url] = __router__[url] || [];
  __router__[url].push(option);
}

/**
 * 注册路由，路由信息是通过装饰器收集的
 * router.head - HEAD
 * router.options - OPTIONS
 * router.get - GET
 * router.put - PUT
 * router.post - POST
 * router.patch - PATCH
 * router.delete - DELETE
 * router.del - 由于 delete 是一个保留字，所以提供了一个 delete 方法的别名。
 * router.redirect - 可以对 URL 进行重定向处理，比如我们最经常使用的可以把用户访问的根目录路由到某个主页。
 * @param app Application
 */
export function initRouter(app: Application) {
  const {router} = app;
  Object.keys(__router__).forEach(url => {
    __router__[url].forEach((opt: RouterOption) => {
      router[opt.httpMethod](opt.url, ...opt.beforeMiddlewares, async (ctx: Context) => {
        const ist = new opt.constructorFn(ctx);
        if (opt.validator) {
          // 如果存在validator，则先进行参数校验
          if (opt.httpMethod === 'get') {
            const query = opt.validator.params ? await ctx.validate(opt.validator.params, ctx.request.query) : null;
            await ist[opt.handleName].call(ist, {query});
          } else {
            const query = opt.validator.params ? await ctx.validate(opt.validator.params, ctx.request.query) : null;
            const body = opt.validator.body ? await ctx.validate(opt.validator.body, ctx.request.body) : null;
            await ist[opt.handleName].call(ist, {body, query});
          }
        } else {
          await ist[opt.handleName].call(ist);
        }
        // await ist[opt.handleName].call(ist);
      });
    });
  });
}

/**
 * 收集路由信息，使用@Route装饰器
 */
export function Route(url: string, method: HttpMethod, validator?: Validator, beforeMiddlewares: any[] = []) {
  return function (target: any, funcName: string, _descriptor: PropertyDescriptor) {
    _setRouter(url, {
      httpMethod: method,
      beforeMiddlewares,
      handleName: funcName,
      constructorFn: target.constructor,
      className: target.constructor.name,
      validator,
      url,
    });
  };
}

const PREFIX_ADMIN = '/admin';
const PREFIX_RESOURCE = '/resource';

/**
 * 自动添加/admin前缀的Url路由装饰器
 * 例如 url 为 /sys/user/add, 使用该装饰器可直接变为/admin/sys/user/add
 */
export function AdminRoute(url: string, method: HttpMethod, validator?: Validator, beforeMiddlewares: any[] = []) {
  return function (target: any, funcName: string, _descriptor: PropertyDescriptor) {
    _setRouter(url, {
      httpMethod: method,
      beforeMiddlewares,
      handleName: funcName,
      constructorFn: target.constructor,
      className: target.constructor.name,
      validator,
      url: `${PREFIX_ADMIN}${url}`,
    });
  };
}

/**
 * 自动添加/resource前缀的Url路由装饰器
 */
export function ResourceRoute(url: string, method: HttpMethod, validator?: Validator, beforeMiddlewares: any[] = []) {
  return function (target: any, funcName: string, _descriptor: PropertyDescriptor) {
    _setRouter(url, {
      httpMethod: method,
      beforeMiddlewares,
      handleName: funcName,
      constructorFn: target.constructor,
      className: target.constructor.name,
      validator,
      url: `${PREFIX_RESOURCE}${url}`,
    });
  };
}

