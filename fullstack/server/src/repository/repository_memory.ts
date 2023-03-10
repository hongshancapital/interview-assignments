/**
 * 这是一个内存的 repo ，主要用于方便测试
 * 同时对于前后端开发时，非常利于先部署一个仅内存的逻辑，而不用等数据库开发完
 */
export async function createMemoryRepository() : Promise<UrlRepository> {
  let idSeed = 1

  const initialUrlData = {
    id: 0,
    url: 'http://example.com/abc',
    short: '0t7lfuZ8',
    createTime: Date.now(),
    refreshTime: Date.now(),
  }

  const cacheByUrl: { [key:string]: UrlStoreData } = {
    [initialUrlData.url]: initialUrlData
  }
  const cacheByShort: { [key:string]: UrlStoreData } = {
    [initialUrlData.short]: initialUrlData
  }

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