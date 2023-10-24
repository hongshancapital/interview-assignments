import { PromisedDatabase } from "promised-sqlite3";
import { initSqlConnection } from "../db/sqlite";
import { Database } from "../lib/sqlite-async";

/**
 * 这是一个内存的 repo ，主要用于方便测试
 * 同时对于前后端开发时，非常利于先部署一个仅内存的逻辑，而不用等数据库开发完
 */
export function createSqlRepository(db: Database) : UrlRepository {

  async function createId(): Promise<number> {
    const result = await db.run('INSERT INTO url_id (nop) VALUES(?)', [0])
    return result.lastID
  }

  async function queryByUrl(url: string): Promise<UrlStoreData | undefined> {
    const row = await db.get<UrlStoreData>("SELECT * FROM url_data WHERE url = ?", url)
    return row
  }

  async function queryByShort(short: string): Promise<UrlStoreData | undefined> {
    const row = await db.get<UrlStoreData>("SELECT * FROM url_data WHERE short = ?", short)
    return row
  }
  
  async function save(data: UrlStoreData) {
    const sql = "INSERT INTO url_data (id, short, url, createTime, refreshTime) VALUES (?, ?, ?, ?, ?)"
    await db.run(sql, [data.id, data.short, data.url, data.createTime, data.refreshTime]);
  }

  return {
    createId,
    queryByUrl,
    queryByShort,
    save
  }
}