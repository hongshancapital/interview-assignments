import Router from 'koa-router';
import { redirectRealUrl } from '../services/shortCode';
import shortCodeRouter from './shortCode';

const router = new Router();

// 重定向
router.get('/:shortCode', redirectRealUrl);

// 其它路由
router.use(shortCodeRouter.routes());

export default router;
