import * as express from 'express';
import { Router, Request, Response, NextFunction } from 'express';
import * as bodyParser from 'body-parser';
import { ShortUrlRepo } from './repositroy'
import { ContentType, StatusCode, ResponseData } from '../models/http'
import { Shorter } from './lib';

const router: Router = express.Router();

/**
 * get shorturl by short_path
 */
 router.get('/s/:short_path', async (req: Request, resp: Response, next: NextFunction) => {
    const shortPath = req.params.short_path;
    if (shortPath.length === 0 || shortPath.length > 8) {
        return resp.status(StatusCode.UnprocessableEntity).json(new ResponseData(`invalid parameter`));
    }
    const id = Shorter.strToId(shortPath);
    if (id <= 0) {
        return resp.status(StatusCode.UnprocessableEntity).json(new ResponseData(`invalid parameter`));
    }
    const model = await new ShortUrlRepo().findUnique({ id });
    if (model === null) {
        return resp.status(StatusCode.ResourceNotFound).json(new ResponseData(`resource not found`));
    }
    resp.redirect(model.long_url);
});

/**
 * get shorturl by short_path
 */
router.get('/api/shorturls/:short_path', async (req: Request, resp: Response, next: NextFunction) => {
    const shortPath = req.params.short_path;
    if (shortPath.length === 0 || shortPath.length > 8) {
        return resp.status(StatusCode.UnprocessableEntity).json(new ResponseData(`invalid parameter`));
    }
    const id = Shorter.strToId(shortPath);
    if (id <= 0) {
        return resp.status(StatusCode.UnprocessableEntity).json(new ResponseData(`invalid parameter`));
    }
    const model = await new ShortUrlRepo().findUnique({ id });
    if (model === null) {
        return resp.status(StatusCode.ResourceNotFound).json(new ResponseData(`resource not found`));
    }
    resp.status(StatusCode.Success).json(new ResponseData(`success`, model));
});

/**
 * create shorturl
 */
router.post('/api/shorturls',
    (req: Request, resp: Response, next: NextFunction): void => {
        if (req.headers['content-type'] !== ContentType.ApplicationJson) {
            resp.status(415).json(new ResponseData(`content-type only accept ${ContentType.ApplicationJson}`));
            return;
        }
        next();
    },
    bodyParser.json(),
    async (req: Request, resp: Response, next: NextFunction) => {
        const { long_url } = req.body;
        if (!long_url || long_url.length === 0) {
            return resp.status(StatusCode.UnprocessableEntity).json(new ResponseData(`invalid parameter`));
        }
        try {
            const model = await new ShortUrlRepo().create({ long_url });
            resp.status(StatusCode.Success).json(new ResponseData(`success`, model));
        } catch (e) {
            resp.status(StatusCode.ServerFailed).json(new ResponseData(`failed`, e));
        }
    });

export default router;
