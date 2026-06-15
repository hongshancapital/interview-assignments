import { createClient } from 'redis'
import env from '../config/env'
const redisClient = createClient({
  url: env.redisUrl,
})
export async function initializeRedis() {
  await redisClient.connect()
}

type KeyFunc<T extends Func> = (...arg: Parameters<T>) => string
/**
 * 缓存的高阶函数，用于使用缓存的场景
 * @param fn
 * @param keyFn 缓存key生成器
 * @param expireTime 过期时间，如不设置默认使用站点设置的缓存失效时间
 * @returns
 */
export function withCache<T extends Func>(
  fn: T,
  keyFn: KeyFunc<T>,
  expireTime?: number,
) {
  return async function (...args: Parameters<T>) {
    const cacheKey = keyFn(...args)
    let value = await redisClient.get(cacheKey)
    if (value === null) {
      value = await fn(...args)
    }
    if (value !== null) {
      await redisClient.setEx(
        cacheKey,
        expireTime ?? env.cacheExpireTime,
        value,
      )
    }

    return value
  }
}

export default redisClient
