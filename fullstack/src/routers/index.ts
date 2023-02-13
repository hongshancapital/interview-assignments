import { Router } from 'express';
import ShortUrlController from '../modules/short-url/controller';

const router  = Router();

router.use('/api',ShortUrlController);

export default router;
