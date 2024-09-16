import { RedisClientType } from "@redis/client"
import { RedisDefaultModules, RedisFunctions, RedisModules, RedisScripts } from "redis"

export function createRedisRepository<M extends RedisModules, F extends RedisFunctions, S extends RedisScripts>(
  client: RedisClientType<RedisDefaultModules & M, F, S>
): UrlRepository {

  async function createId(): Promise<number> {
    return await client.INCR('url_id');
  }

  async function queryByUrl(url: string): Promise<UrlStoreData | undefined> {
    const short = await client.get(`url_data_url_${url}`)
    if (!short) return
    return queryByShort(short)
  }

  async function queryByShort(short: string): Promise<UrlStoreData | undefined> {
    const urlData = await client.get(`url_data_short_${short}`)
    if (!urlData) return undefined
    return JSON.parse(urlData)
  }
  
  async function save(data: UrlStoreData) {
    const multi = client.multi()
    multi.set(`url_data_url_${data.url}`, data.short)
    multi.set(`url_data_short_${data.short}`, JSON.stringify(data))
    await multi.exec()
  }

  return {
    createId,
    queryByUrl,
    queryByShort,
    save
  }
}