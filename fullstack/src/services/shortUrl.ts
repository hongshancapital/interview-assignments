import { collections } from '../components/database'
import * as bloomFilter from '../components/bloomFilter'
import env, { Env } from '../config/env'
import HashKeyGenarator, { KeyGenerator } from './HashKeyGenerator'
import IdKeyGenarator from './IdKeyGenerator'
const SHORT_URL_FILTER = 'shorurl:filter'

export class ShortUrlService {
  keyGenerator: KeyGenerator
  constructor(envConfig: Env) {
    this.keyGenerator = envConfig.useHash
      ? new HashKeyGenarator({ shortUrlFilter: SHORT_URL_FILTER })
      : new IdKeyGenarator(envConfig)
  }

  /**
   * 获取映射信息
   * @param shortUrlKey
   * @returns
   */
  getByKey = async (shortUrlKey: string) => {
    if (!(await bloomFilter.exists(SHORT_URL_FILTER, shortUrlKey))) {
      return null
    }
    const urlMapping = await collections.UrlMapping.findOne({
      _id: shortUrlKey,
    })
    if (urlMapping === null) {
      return null
    }
    return urlMapping
  }
  /**
   * 生成链接映射实体
   * @param longUrl 长链接地址
   * @returns
   */
  create = async (longUrl: string) => {
    const shortUrlKey = await this.keyGenerator.generateKey(env.keyLength)
    const urlMapping = { longUrl, _id: shortUrlKey }
    await collections.UrlMapping.insertOne(urlMapping)
    await bloomFilter.add(SHORT_URL_FILTER, shortUrlKey)
    return urlMapping
  }
}
export default new ShortUrlService(env)
