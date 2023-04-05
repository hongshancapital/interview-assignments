/**
 * @file 项目统一入口页面
 * @author zengbaoqing<misterapptracy@gmail.com>
 */
'use strict';
import { Application } from 'egg';

export default (app: Application) => {
  // 应用会等待这个函数执行完成才启动
  app.beforeStart(async () => {
    // 同步表结构
    // app.model.sync();
  });
};
