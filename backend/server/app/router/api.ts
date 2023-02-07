import { Application } from 'egg';

export default (app: Application) => {
    const {controller, router} = app;
    //获取短域名
    router.get('/api/encodeUrl', controller.siteController.encodeUrl);
    //解析短域名
    router.get('/:id', controller.siteController.decodeUrl);
};
