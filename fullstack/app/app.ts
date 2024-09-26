import express, { Application } from 'express';
import {getShortUrlApi, getOriginUrlApi} from './route/api';

const app: Application = express();

app.use(express.json({ type: 'application/json' }));
app.use(express.urlencoded({ extended: false }));

app.route('/api/shorturl')
  .get(getOriginUrlApi)
  .post(getShortUrlApi) // x-www-form-urlencoded
;

const server = app.listen(8080);

export { app, server };