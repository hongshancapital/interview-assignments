import fs from 'fs'
import path from 'path'
import { Database } from '../lib/sqlite-async'

const schema = fs.readFileSync(path.join(__dirname, 'schema.sql'), 'utf-8')

export async function initSqlConnection() {
  // 这里是作业先用 SQLite 代替，实际情况会用 MySQL 之类的
  const db = await Database.open(':memory:')
  
  // run schema
  const sqlList = schema.split(';')
  for(var i=0; i<sqlList.length; i++) {
    const sql = sqlList[i];
    if (sql.trim()) {
      await db.run(sql)
    }
  }

  return db
}