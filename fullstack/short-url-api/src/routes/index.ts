import { Router } from 'express';
import shortUrlRouter from './shortUrlRouter';

const apiRouter = Router();

apiRouter.use('/shortUrl', shortUrlRouter);

export default apiRouter;
