import {DbAccess} from "./db-access";
import {IdTranslator} from "./id-translator";
import {Cache} from "./cache"

/**
 * 控制器
 */
export class Controller {
  private dbAccess: DbAccess;
  private cache: Cache;
  private idTranslator: IdTranslator;

  static CACHE_TIME = 3600;

  constructor(dbAccess: DbAccess, cache: Cache, idTranslator: IdTranslator) {
    this.dbAccess = dbAccess;
    this.cache = cache;
    this.idTranslator = idTranslator;
  }

  async getUrl(shortUrl: string): Promise<string | null> {
    const id = this.idTranslator.decode(shortUrl);
    if (!id) {
      return null;
    }

    let url = await this.cache.get(id.toString());
    if (url) {
      return url;
    }

    url = await this.dbAccess.getUrlById(id);
    //不管数据库是否存在，都缓存一下，防止不存在的 url 造成数据库压力
    await this.cache.set(id.toString(), url || '', Controller.CACHE_TIME);

    return url;
  }

  async saveUrl(url: string): Promise<string> {
    const exists = await this.dbAccess.getIdByUrl(url);
    if (exists) {
      return this.idTranslator.encode(exists);
    }

    const id = await this.dbAccess.save(url);
    await this.cache.set(id.toString(), url, Controller.CACHE_TIME);
    return this.idTranslator.encode(id);
  }
}
