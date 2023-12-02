import { Express } from 'express';
import Redis, { RedisOptions } from 'ioredis-mock';

export default function (app: Express, redisOptions?: RedisOptions) {
  const redis = new Redis(redisOptions);
  app.use((req, res, next) => {
    req.redis = redis;
    next();
  });
  return redis;
}
