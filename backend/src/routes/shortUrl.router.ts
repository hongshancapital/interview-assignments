import { Router } from 'express';

import shortUrlController from '../controller/shortUrl.controller';

const router: Router = Router();

router.post('/generate', shortUrlController.generate);
router.get('/getOriginUrl', shortUrlController.getOriginUrl);

export { router };
