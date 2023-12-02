import { Express } from 'express';
import ioredis = require('ioredis');
import * as bodyParser from 'body-parser';
import * as cors from 'cors';

declare global {
  namespace Express {
    interface Request {
      redis: ioredis.Redis;
    }
  }
}

export default function (app: Express) {
  app.use(cors());

  app.use(bodyParser.urlencoded({ extended: false }));
  app.use(bodyParser.json());

  app.use((req, res, next) => {
    // TODO 注入redis
    // req.redis = redis;
    next();
  });
}
