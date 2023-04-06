import { Request, Response, Router } from 'express';
import unshorten from './unshorten';
import shorten from './shorten';
import logger from '../log/log';

const router = Router();
router.get('/favicon.ico', (_, res: Response) => res.sendStatus(204).end());

router.get('/:code', (req: Request, res: Response) => {
    unshorten(req.params.code)
    .then((url) => {
        res.status(200).send({'url': url}).end();
    })
    .catch((err) => {
        logger.debug('Unshorten failed.', err);
        res.status(err.status).send(err).end();
    });
});
router.post('/shorten', (req: Request, res: Response) => {
    shorten(req.body?.url)
    .then((code) => {
        res.status(200).send({'code': code}).end();
    })
    .catch((err) => {
        logger.info('Shorten failed.', err);
        res.status(err.status).send(err).end();
    });
});

export default router;