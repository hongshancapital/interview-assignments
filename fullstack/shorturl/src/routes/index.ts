import { Router } from 'express';

import shortRouter from './short';

import redirectRouter from './redirect';

const router = Router();

router.use('/', redirectRouter);

router.use('/api/v1', shortRouter);

export default router;