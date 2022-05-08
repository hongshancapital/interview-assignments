import { Application } from 'egg';

export default (app: Application) => {
  const { controller, router } = app;
  router.get('/:tinyUrl', controller.urlMap.redirectOriginalUrl);
  router.get('/api/tinyUrl', controller.urlMap.getOriginalUrl);
  router.post('/api/tinyUrl', controller.urlMap.setInfo);
  router.delete('/api/tinyUrl', controller.urlMap.delUrl);
};
