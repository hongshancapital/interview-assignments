type UrlStoreData = {
  id: number          // 唯一id
  short: string       // 短链
  url: string         // 原始链接
  createTime: number  // 创建时间
  refreshTime: number // 刷新时间
}

type UrlRepository = {
  async createId(): Promise<number>
  async queryByUrl(url: string): Promise<UrlStoreData | undefined>
  async queryByShort(short: string): Promise<UrlStoreData | undefined>
  async save(data: UrlStoreData)
}

type UrlService = {
  async createOrQueryShort(url: string): Promise<UrlStoreData>
  async queryByShort(short: string): Promise<UrlStoreData | undefined>
}