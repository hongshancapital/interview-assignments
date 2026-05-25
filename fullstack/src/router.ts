import KoaRouter from '@koa/router'
import { getUrlByShortIdHandler, generateShortUriHandler } from '@/controller'

const router = new KoaRouter();
router.get('/:shortId', getUrlByShortIdHandler);
router.post('/generate-short-uri', generateShortUriHandler)

export default router