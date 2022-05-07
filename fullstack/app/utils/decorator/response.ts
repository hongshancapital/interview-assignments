import { BaseContextClass, Context } from 'egg';
import { IPageResponse, IResponse, IPaging } from 'web-api';

const promiseFunc = {
  data(ctx: Context, data: unknown) {
    ctx.body = {
      success: true,
      data,
    } as IResponse<unknown>;
  },

  page(ctx: Context, res: { paging: IPaging, data: unknown[] }) {
    ctx.body = {
      success: true,
      data: res.data,
      paging: res.paging,
    } as IPageResponse<unknown>;
  },

  file(ctx: Context, res: { name: string, file: Buffer }) {
    ctx.body = res.file;
    ctx.attachment(res.name);
  },

  redirect(ctx: Context, res: { url: string }) {
    ctx.redirect(res.url);
  },
};

/**
 * 装饰器类型
 * @param _target 类的原型对象
 * @param _propertyKey 所要装饰的属性名
 * @param descriptor 该属性的描述对象
 * @param callback 回调函数，promiseFunc 中的 function
 * @return {PropertyDescriptor} descriptor
 */
function decorator(_target: BaseContextClass, _propertyKey: string, descriptor: PropertyDescriptor, callback: any): PropertyDescriptor {
  const oldValue = descriptor.value;
  descriptor.value = function newValue() {
    // eslint-disable-next-line prefer-rest-params
    const promise = oldValue.apply(this, arguments);
    if (promise.then) {
      promise.then((res: any) => {
        const { ctx } = this as any;
        callback(ctx, res);
      });
    }
    return promise;
  };
  return descriptor;
}

function response(target: BaseContextClass, propertyKey: string, descriptor: PropertyDescriptor) {
  return decorator(target, propertyKey, descriptor, promiseFunc.data);
}

function pageResponse(target: BaseContextClass, propertyKey: string, descriptor: PropertyDescriptor) {
  return decorator(target, propertyKey, descriptor, promiseFunc.page);
}

function fileResponse(target: BaseContextClass, propertyKey: string, descriptor: PropertyDescriptor) {
  return decorator(target, propertyKey, descriptor, promiseFunc.file);
}

function redirect(target: BaseContextClass, propertyKey: string, descriptor: PropertyDescriptor) {
  return decorator(target, propertyKey, descriptor, promiseFunc.redirect);
}

export {
  response,
  pageResponse,
  fileResponse,
  redirect,
};

