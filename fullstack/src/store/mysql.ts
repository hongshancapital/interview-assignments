import mysql, { Pool } from 'mysql2'
import { MySQLConfig, URLRecord } from '../types/type'


class MySQLStore {
  private pool: Pool
  constructor(config: MySQLConfig) {
    this.pool = mysql.createPool({
      host: config.host,
      user: config.user,
      database: config.database,
      waitForConnections: config.waitForConnections || true,
      password: config.pass,
      connectionLimit: config.connectionLimit || 10,
      queueLimit: 0,
    })

    this.getURLByID = this.getURLByID.bind(this)
    this.insertURL = this.insertURL.bind(this)
    this.execute = this.execute.bind(this)
  }

  async getURLByID(id: number) {
    try {
      const sql = 'SELECT * FROM url where id = ?'
      const res = await this.execute<URLRecord[]>(sql, [ id ]);
      return res[0]
    } catch (error) {
      throw new Error('failed to get url')
    }
    
  }

  async insertURL(longURL: string) {
    try {
      const sql = 'INSERT INTO url (long_url) VALUES (?)'
      const res = await this.execute<{insertId: number}>(sql, [ longURL ]);
      return res.insertId
    } catch (error) {
      // ER_DUP_ENTRY
      if ((error as any).errno === 1062) {
        
      }
      console.error(error);
      throw new Error('failed to insert url')
    }
    
  }

  async execute<T>(sql: string, params: Array<string | number>): Promise<T> {
    return new Promise((resolve, reject) => {
      this.pool.execute(sql, params, (err, result) => {
        if (err) {
          reject(err)
        } else {
          resolve(result as any)
        }
      })
    })
  }

}

export { MySQLStore }