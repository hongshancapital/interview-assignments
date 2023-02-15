import Express from 'express';
import bodyParser from 'body-parser';

import { Router } from './router';

const app = Express();

app.use(bodyParser.json());

const router = new Router();

app.post('/saveLink', router.saveLink.bind(router));
app.get('/getLink', router.getLink.bind(router));

export default app;
