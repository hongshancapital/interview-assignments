import { Router, Request, Response } from 'express';
import { findShortUrl, findLongUrl } from '../src/controller/api.controller';
const router: Router = Router();

router.get('/findShortUrl/:url', findShortUrl);

router.get('/findLongUrl/:url', findLongUrl);

module.exports = router;