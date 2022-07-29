import { Application } from 'egg';

export default (app: Application) => {
  const { controller, router } = app;

  router.get('/:key', controller.home.index);
  router.get('/short-link/create', controller.home.create);
};
