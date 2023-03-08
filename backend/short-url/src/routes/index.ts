import { Router } from 'express';
import shortUrlRouter from './short-url';

const apiRouter = Router();

apiRouter.use('/short-url', shortUrlRouter);

export default apiRouter;
