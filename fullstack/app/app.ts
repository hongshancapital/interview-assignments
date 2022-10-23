import express, { Application, Request, Response } from 'express';
import {getShortUrlApi, getOriginUrlApi} from './route/route';

const app: Application = express();

app.route('/api/shorturl/:url')
  .get(getOriginUrlApi)
  .post(getShortUrlApi)
;

app.listen(8080);