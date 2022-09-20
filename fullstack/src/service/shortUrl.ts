import { ShortUrlData, ShortUrlKeys } from './../types/shortUrl';
import shortUrlDao from "../data/shortUrl"
import { GenerateUrlParam } from "../types/shortUrl"

class ShortUrlService {
  async generate(param: GenerateUrlParam): Promise<string> {
    // 查询长链接是否已经存在
    const existData = await shortUrlDao.checkExist(param.originUrl)
    // 长链接已经存在，则直接返回对应的短链接地址
    if (existData) {
      return existData.shortUrl
    }
    // 查询是否存在已作废的记录
    const existDeletedData = await shortUrlDao.queryDeleted()
    if (existDeletedData) {
      // 更新入库，保存长链接
      await shortUrlDao.update({
        originUrl: param.originUrl,
        isDelete: false
      }, existDeletedData.id)
      // 返回已经存在的短链接
      return existDeletedData.shortUrl
    }
    // 长链接还不存在，则将长链接保存入库
    const insertId = await shortUrlDao.add(param.originUrl)
    // 根据自增id创建短链接地址
    const url = this.getUrlById(insertId)
    if (url.length > 8) {
      throw new Error('连接池已满!!')
    }
    // 更新数据库记录，保存短链接地址
    await shortUrlDao.update({
      shortUrl: url,
    }, insertId)
    // 返回短链接地址
    return url
  }

  /**
   * 根据id生成短链接
   * @param id 
   * @returns 
   */
  getUrlById(id: number): string {
    const keysLength = ShortUrlKeys.length
    if (id > keysLength) {
      let prefixCount = Math.floor(id / keysLength)
      const res = []
      while (prefixCount > 0) {
        res.push(ShortUrlKeys[keysLength - 1])
        prefixCount -= 1
      }
      res.push(ShortUrlKeys[id % keysLength])
      return res.join('')
    }
    return ShortUrlKeys[id - 1] // 因为id从1开始，因此减1
  }

  async getOriginByShort (url: string): Promise<ShortUrlData | undefined> {
    const id = this.getIdByShortUrl(url)
    const res = await shortUrlDao.getById(id)
    return res
  }

  getIdByShortUrl (url: string): number {
    let id = 0
    const urlLength = url.length
    if (urlLength === 1) {
      return ShortUrlKeys.indexOf(url)
    }
    id = ShortUrlKeys.length * (urlLength - 1)
    id += ShortUrlKeys.indexOf(url.charAt(urlLength - 1))
    return id
  }
}

const shortUrlService = new ShortUrlService()

export default shortUrlService