import Router from 'koa-router';
import ApiRoute from './api';

const router = new Router();

router.use('/api', ApiRoute.routes());

export default router;