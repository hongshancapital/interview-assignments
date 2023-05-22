import express from 'express';
import { param } from 'express-validator';

import { ShortenController } from './shortenController';

const shortenCtl = new ShortenController();
export const router = express.Router();

router.get('/original/:url', param('url').isLength({max: 8}), shortenCtl.getOriginalUrl);
router.post('/', shortenCtl.genShortUrl);
