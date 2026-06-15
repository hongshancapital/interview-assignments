import { Next } from 'koa';
import Router from 'koa-router';
import { MethodParams, IContext } from "./types/common";

export function routes (controllers: any[]) {
    const globalRouter = new Router();
    for (const controller of controllers) {
        wrapController(globalRouter, controller);
    }

    // console.log(globalRouter.stack.map((i) => i.path));
    return globalRouter;
}
export function methodManager (params: MethodParams): MethodDecorator {
    const {
        path = '',
        middlewares = [],
        httpMethod
    } = params;
    return (target: any, propertyKey: string | symbol, description: PropertyDescriptor) => {
        if (!target.constructor.prototype.paths) {
            target.constructor.prototype.paths = {};
        }
        target.constructor.prototype.paths[propertyKey] = {
            path,
            middlewares,
            httpMethod
        };
    };
}

export function wrapController (globalRouter: Router, controller: any): void {
    const routeInfos = controller.prototype;
    const crouter = new Router();
    for (const method of Object.keys(routeInfos.paths)) {
        const routeInfo = routeInfos.paths[method];
        crouter[routeInfo.httpMethod.toLowerCase()](
            routeInfo.path,
            ...routeInfo.middlewares,
            method2middleware(controller, method),
        );
    }
    if (crouter.stack.length !== 0) {
        globalRouter.use('', crouter.routes(), crouter.allowedMethods());
    }
}

function method2middleware (Controller: any, method: string) {
    return (ctx: IContext, next: Next) => {
        const instance = new Controller(ctx);
        const fn = instance[method];
        return fn.call(instance);
    };
}
