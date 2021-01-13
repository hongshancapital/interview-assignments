import express = require('express');
import { ShortUrlModule } from './modules/short-url/short-url.module';
import { connectDb } from './libs/db.lib';
import { connectRedis } from './libs/redis.lib';

const app: express.Application = express();

app.use('/', ShortUrlModule.routes);

export const appInit = async () => {
  await connectDb();
  await connectRedis();
  await ShortUrlModule.service.initCounter();
  return app;
};
