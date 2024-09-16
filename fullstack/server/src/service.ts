import { number2Short } from "./algorithm";

export function createService(repository: UrlRepository): UrlService {

  async function createOrQueryShort(url: string): Promise<UrlStoreData> {
    const time = Date.now()

    let urlData = await repository.queryByUrl(url)
    if (!urlData) {
      const id = await repository.createId();
      const short = number2Short(id, 8)
      urlData = { id, short, url, createTime: time, refreshTime: time }
    } else {
      urlData.refreshTime = time
    }
    await repository.save(urlData)

    return urlData
  }
  async function queryByShort(short: string): Promise<UrlStoreData | undefined> {
    const urlData = await repository.queryByShort(short)
    return urlData
  }

  return {
    createOrQueryShort,
    queryByShort,
  }
}