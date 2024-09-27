import redisService from '../services/redis.service';
import { Request, Response, NextFunction } from 'express';

const redisClient = redisService.getClient();

export const RATE_LIMIT_MAX_REQUESTS = 15; // 时间窗口内最大请求次数
export const RATE_LIMIT_BLACKLIST_EXPIRE = 60 * 60 * 6; // 黑名单过期时间，单位为秒
export const DAILY_TOTAL_LIMIT_EXPIRE = 60 * 60 * 24; 
export const DAILY_TOTAL_LIMIT_MAX_REQUESTS = 500;
export const  RATE_LIMIT_SCRIPT = `
  local key = KEYS[1]
  local value = redis.call('get', key)
  if value then
    redis.call('incr', key)
  else
    redis.call('set', key, 1, 'EX', 1)
  end
  return redis.call('get', key)
`;

export const DAILY_TOTAL_LIMIT_SCRIPT = `
  local key = KEYS[1]
  local value = redis.call('get', key)
  if value then
    redis.call('incr', key)
  else
    redis.call('set', key, 1, 'EX', ${DAILY_TOTAL_LIMIT_EXPIRE})
  end
  return redis.call('get', key)
`;

export const isBlack = async (req: Request, res: Response, next: NextFunction) => {
  const userUid = ((req as any).user?.uid ?? 'undefined') as string;

  const key = `${req.ip}:${userUid}`;
  const blackKey = redisService.getKey(`black:${key}`);
  const dailyRequestsKey = redisService.getKey(`requests:total:${key}`);
  const windowRequestsKey = redisService.getKey(`requests:window:${key}`);
  try {
    const exists = await redisClient.exists(blackKey);
    if (exists) {
      return res.status(403).json({ status: 403, message: "Forbidden."});
    }

    const dailyResult = await redisClient.eval(DAILY_TOTAL_LIMIT_SCRIPT, {
      keys: [dailyRequestsKey],
    });
    if (dailyResult as number> DAILY_TOTAL_LIMIT_MAX_REQUESTS) {
      await redisClient.set(blackKey, 1, {
        EX: RATE_LIMIT_BLACKLIST_EXPIRE,
        NX: true,
      });
      return res.status(429).json({ status: 429, message: "Too Many Requests Tody."});
    }

    const result = await redisClient.eval(RATE_LIMIT_SCRIPT, {
      keys: [windowRequestsKey],
    });
    if(result as number > RATE_LIMIT_MAX_REQUESTS) {
      await redisClient.set(blackKey, 1, {
        EX: RATE_LIMIT_BLACKLIST_EXPIRE,
        NX: true,
      });
      return res.status(403).json({ status: 403, message: "Forbidden."});
    }
    next();
  } catch(error) {
    next(error);
  }
};