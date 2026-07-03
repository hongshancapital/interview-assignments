import { Configuration, App } from '@midwayjs/decorator';
import * as koa from '@midwayjs/koa';
import * as validate from '@midwayjs/validate';
import * as info from '@midwayjs/info';
import { join } from 'path';
import { ReportMiddleware } from './middleware/report.middleware';
import * as view from '@midwayjs/view-nunjucks';
import * as orm from '@midwayjs/typeorm';
import * as redis from '@midwayjs/redis';
@Configuration({
  imports: [

    koa,
    validate,
    {
      component: info,
      enabledEnvironment: ['local'],
    },
    view,
    redis,
    orm,
  ],
  importConfigs: [join(__dirname, './config')],
})
export class ContainerLifeCycle {
  @App()
  app: koa.Application;

  async onReady() {
    // add middleware
    this.app.useMiddleware([ReportMiddleware]);
    // add filter
    // this.app.useFilter([WeatherErrorFilter]);
  }
}
