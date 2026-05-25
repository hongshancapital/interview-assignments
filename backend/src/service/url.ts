import { to62Str } from '../util/common'
import LRU from 'lru-cache'
import ShortUrlMap from '../model/ShortUrlMap'
import logger from '../util/logger'

const murmurhash = require('murmurhash')

const options = {
  max: 100000, //最多存10万条
  maxAge: 1000 * 60 * 60, //最多存1小时
}
const urlMapCache = new LRU<string, string>(options)
export const EMPTY_GUARD = 'EMPTY_GUARD'
const MAX_RETRY_TIMES = 5

/**
 * 将长url缩短，并将映射关系存库
 * @param longUrl
 */
export async function shortenUrlAndSave(longUrl: string): Promise<string | undefined> {
  let shortUrl = to62Str(murmurhash.v3(longUrl))
  let retryTimes = MAX_RETRY_TIMES
  do {
    const exist = await ShortUrlMap.findOne({
      where: {
        short_url: shortUrl,
      },
    })
    //如果没冲突，直接存库、更新缓存并返回
    if (!exist) {
      longUrl = longUrl.replace(/[DUPLICATE]/g, '')
      await ShortUrlMap.create({
        short_url: shortUrl,
        long_url: longUrl,
      })
      return shortUrl
    }
    //如果哈希冲突了，那么添加后缀重新哈希，最多尝试5次
    if (retryTimes > 1) {
      longUrl = `${longUrl}[DUPLICATE]`
      shortUrl = to62Str(murmurhash.v3(longUrl))
      retryTimes--
      continue
    }
    logger.error(`still conflict after retry: ${longUrl} `)
    //如果一直冲突，直接返回空串
    return ''
  } while (retryTimes)
}

/**
 * 通过短 url path 查询对应的原始网址，会优先从 LRU 缓存获取
 * @param shortUrl
 */
export async function getLongUrlByShort(shortUrl: string): Promise<string | undefined> {
  const cachedLongUrl = urlMapCache.get(shortUrl)
  //如果查询到有效缓存，直接返回
  if (cachedLongUrl && cachedLongUrl !== EMPTY_GUARD) {
    return cachedLongUrl
  }
  //缓存里有无效值，返回空串，
  if (cachedLongUrl === EMPTY_GUARD) {
    return ''
  }
  const shortUrlMap = await ShortUrlMap.findOne({
    where: {
      short_url: shortUrl,
    },
  })
  //数据库里有结果，更新缓存并返回
  if (shortUrlMap) {
    urlMapCache.set(shortUrl, shortUrlMap.long_url)
    return shortUrlMap.long_url
  }
  logger.error(`get invalid short url ${shortUrl}`)
  //缓存里设置空值，返回空串
  urlMapCache.set(shortUrl, EMPTY_GUARD)
  return ''
}

/**
 * 从 db 查询长网址对应的短网址
 * @param longUrl
 */
export async function getShortUrlByLong(longUrl: string): Promise<string | undefined> {
  const shortUrlMap = await ShortUrlMap.findOne({
    where: {
      long_url: longUrl,
    },
  })
  return shortUrlMap && shortUrlMap.short_url
}

/**
 * 从 缓存 查询短网址对应的长网址
 * @param shortPath
 */
export function getLongByShortFromCache(shortPath: string) {
  return urlMapCache.get(shortPath)
}
