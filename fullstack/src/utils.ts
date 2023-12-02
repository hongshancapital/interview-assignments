import * as murmurhash from 'murmurhash';
import Redis from 'ioredis-mock';
import * as express from 'express';
import buildInMiddlewares from './middlewares';
import sUrl from './sUrl';

const base62 = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
export function toBase62(num: number) {
  const base = base62.length;
  let str = '';
  while (num > 0) {
    str = base62[num % base] + str;
    num = Math.floor(num / base);
  }
  return str;
}

export function hash(value: string, seed?: number) {
  return murmurhash.v3(value, seed);
}

export function initTestApp(data?: { [key: string]: any }) {
  const redis = new Redis({
    data,
  });

  const app = express();
  buildInMiddlewares(app);

  app.use((req, res, next) => {
    req.redis = redis;
    next();
  });

  sUrl(app);

  return { app, redis };
}
