import { default as Redis } from 'ioredis';
import config from 'config';
// TODO: This expression is not constructable.
// ioredis node16兼容性问题，官方尚未彻底解决
// @ts-ignore
const redisClient = new Redis(config.get('db.redis'));
export default function redis(req, res, next) {
  req.redisClient = redisClient;
  next();
}
