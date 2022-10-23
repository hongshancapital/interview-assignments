import Express from 'express';

import { Router } from './router';

const app = Express();

const router = new Router();

app.post('/saveLink', router.saveLink);
app.get('/getLink', router.getLink);

export default app;
