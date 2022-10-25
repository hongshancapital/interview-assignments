import express, { Application, Request, Response } from 'express';
import {getShortUrlApi, getOriginUrlApi} from './route/route';

const app: Application = express();

app.use(express.urlencoded({ extended: false }));

app.route('/api/shorturl')
  .get(getOriginUrlApi)
  .post(getShortUrlApi) // x-www-form-urlencoded
;

app.listen(8080);