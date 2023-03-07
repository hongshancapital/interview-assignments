import { Router } from 'express';
import shortUrlRouter from './short-url';

const apiRouter = Router();

apiRouter.use('/shortUrl', shortUrlRouter);

export default apiRouter;
