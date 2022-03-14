import { Configuration, App } from '@midwayjs/decorator';
import * as koa from '@midwayjs/koa';
import * as validate from '@midwayjs/validate';
import * as info from '@midwayjs/info';
import { join } from 'path';
// import { DefaultErrorFilter } from './filter/default.filter';
// import { NotFoundFilter } from './filter/notfound.filter';
import { ReportMiddleware } from './middleware/report.middleware';
import { ErrorCheckMiddleware } from './middleware/errorcheck.middleware';
import * as sequlize from '@midwayjs/sequelize';
import * as redis from '@midwayjs/redis';

@Configuration({
  imports: [
    koa,
    validate,
    {
      component: info,
      enabledEnvironment: ['local'],
    },
    sequlize,
    redis,
  ],
  importConfigs: [join(__dirname, './config')],
})
export class ContainerLifeCycle {
  @App()
  app: koa.Application;

  async onReady() {
    // add middleware
    this.app.useMiddleware([ReportMiddleware, ErrorCheckMiddleware]);

    // add filter
    // this.app.useFilter([NotFoundFilter, DefaultErrorFilter]);
  }
}
