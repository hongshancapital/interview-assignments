import { RedisClientType } from "@redis/client"
import { RedisDefaultModules, RedisFunctions, RedisModules, RedisScripts } from "redis"


export function createMemoryRepository() : UrlRepository {
  let idSeed = 0
  const cacheByUrl: { [key:string]: UrlStoreData } = {}
  const cacheByShort: { [key:string]: UrlStoreData } = {}

  async function createId(): Promise<number> {
    return idSeed++;
  }

  async function queryByUrl(url: string): Promise<UrlStoreData | undefined> {
    return cacheByUrl[url]
  }

  async function queryByShort(short: string): Promise<UrlStoreData | undefined> {
    return cacheByShort[short]
  }
  
  async function save(data: UrlStoreData) {
    cacheByUrl[data.url] = data
    cacheByShort[data.short] = data
  }

  return {
    createId,
    queryByUrl,
    queryByShort,
    save
  }
}

export function createRedisRepository<M extends RedisModules, F extends RedisFunctions, S extends RedisScripts>(
  client: RedisClientType<RedisDefaultModules & M, F, S>
): UrlRepository {

  async function createId(): Promise<number> {
    return await client.INCR('com_mingo_short_url_id');
  }

  async function queryByUrl(url: string): Promise<UrlStoreData | undefined> {
    return {
    } as any
  }

  async function queryByShort(short: string): Promise<UrlStoreData | undefined> {
    return {
    } as any
  }
  
  async function save(data: UrlStoreData) {
    
  }

  return {
    createId,
    queryByUrl,
    queryByShort,
    save
  }
}