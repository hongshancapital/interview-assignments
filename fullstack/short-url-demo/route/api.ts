import Router from 'koa-router';
import shortUrlController from '../controller/api/shortUrl';

const router = new Router();

router.get('/short', shortUrlController.getShortUrl);
router.get('/origin', shortUrlController.getOriginUrl);

export default router;
