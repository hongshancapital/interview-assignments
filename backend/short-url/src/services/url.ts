import { conn, query } from '../database/mysql'
import { generateShortUrl } from '../utils/url'

export const getShortUrl = async (url: string) => {
  try {
    const sql = `select id, url, short_url from url where url= ${conn.escape(
      url
    )}`
    const rows = (await query(sql)) as any
    console.log(rows)
    if (rows.length == 0) {
      const shortUrl = generateShortUrl(8)
      console.log(shortUrl)
      const insertSql = `insert into url set url=${conn.escape(
        url
      )}, short_url=${conn.escape(shortUrl)}`
      console.log(insertSql)
      const insertRow: any = await query(insertSql)
      console.log(insertRow)

      if (insertRow) {
        return { id: insertRow.insertId, url: url, short_url: shortUrl }
      } else {
        return {}
      }
    } else {
      return rows[0]
    }
  } catch (err) {
    throw err
  }
}

export const getLongUrl = async (shortUrl: string) => {
  try {
    const sql = `select id, url, short_url from url where short_url= ${conn.escape(
      shortUrl
    )}`
    const rows = (await query(sql)) as any
    return rows[0]
  } catch (err) {
    throw err
  }
}
