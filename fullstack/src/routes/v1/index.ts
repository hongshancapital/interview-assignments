import express from 'express';
import {shortenUrlRouter} from './shortUrl/shortUrl'

const router = express.Router();

router.use('/short-url', shortenUrlRouter);

export default router;
