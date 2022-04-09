import express from 'express';
import toolbox from './toolbox';


const apiRouter = express.Router();

apiRouter.use(toolbox);

export default apiRouter;