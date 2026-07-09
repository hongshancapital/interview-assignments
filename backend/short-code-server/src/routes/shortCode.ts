import Router from 'koa-router';
import { spawnShortCode, getShortCodeInfo } from '../services/shortCode';

const router = new Router({ prefix: '/api' });

// 设置短链信息
router.post('/shortCode', spawnShortCode);

// 获取短链信息
router.get('/shortCode', getShortCodeInfo);

export default router;
