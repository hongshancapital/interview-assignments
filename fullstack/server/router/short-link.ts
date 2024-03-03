import { Request, Response, Router } from 'express';
import { find, create } from './../controllers/link.controller';

const router = Router();

router.get('/', (req: Request, res: Response) => {
  res.send('welcome to short-link service')
});

router.get('/getOriginLink', find);

router.post('/generateShortLink', create);

module.exports = router;
