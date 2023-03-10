import { RedisClientType } from "@redis/client"
import { RedisDefaultModules, RedisFunctions, RedisModules, RedisScripts } from "redis"

export function createRedisRepository<M extends RedisModules, F extends RedisFunctions, S extends RedisScripts>(
  client: RedisClientType<RedisDefaultModules & M, F, S>
): UrlRepository {

  async function createId(): Promise<number> {
    return await client.INCR('url_id');
  }

  async function queryByUrl(url: string): Promise<UrlStoreData | undefined> {
    client.hmGet('url_data', 'url')
    return {} as any
  }

  async function queryByShort(short: string): Promise<UrlStoreData | undefined> {
    return {
    } as any
  }
  
  async function save(data: UrlStoreData) {
    await client.hSet('url_data', [
      ['id', data.id],
      ['short', data.short],
      ['url', data.url],
      ['createTime', data.createTime],
      ['refreshTime', data.refreshTime],
    ])
  }

  return {
    createId,
    queryByUrl,
    queryByShort,
    save
  }
}