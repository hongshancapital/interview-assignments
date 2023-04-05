/**
 * @file 路由管理
 * @author zengbaoqing<misterapptracy@gmail.com>
 */
import { Application } from 'egg';

export default (app: Application) => {
  const { router, controller } = app;
  const { shortUrl } = controller;
  router.get('/:shortId', shortUrl.redirect);
  router.post('/create', shortUrl.create);
};
