import { ShortUrlData } from "../types/shortUrl"
import util from "../utils/util"
import pool from "./pool"

class ShortUrlDao {
  async checkExist(url: string): Promise<ShortUrlData | undefined> {
    const sql = `select * from short_url where origin_url = ?`
    const res = await pool.query<ShortUrlData>(sql, [url])
    return res[0]
  }

  async add(url: string): Promise<number> {
    const sql = `insert into short_url(origin_url) values(?)`
    const res = await pool.write(sql, [url])
    return res.insertId
  }

  async update(data: ShortUrlData, id: number): Promise<void> {
    const chunks = []
    const params = []
    Object.keys(data).forEach(key => {
      if (key === 'id') return
      chunks.push(` ${util.toUnderLine(key)}=?`)
      params.push(data[key])
    })
    params.push(id)
    const sql = `update short_url set ${chunks.join(',')} where id = ?`
    await pool.write(sql, params)
  }

  async queryDeleted(): Promise<ShortUrlData | undefined> {
    const sql = `select * from short_url where is_delete = ? limit 1`
    const res = await pool.query<ShortUrlData>(sql, [true])
    return res[0]
  }

  async getById(id: number): Promise<ShortUrlData | undefined> {
    const sql = `select * from short_url where id = ?`
    const res = await pool.query<ShortUrlData>(sql, [id])
    return res[0]
  }
}

const shortUrlDao = new ShortUrlDao()
export default shortUrlDao