import * as koa from '@midwayjs/koa';
export declare class ContainerLifeCycle {
    app: koa.Application;
    onReady(): Promise<void>;
}
