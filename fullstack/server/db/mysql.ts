import mysql, {Pool, PoolConnection, QueryOptions} from 'mysql'
import process from 'process'
import dotenv from 'dotenv'
dotenv.config(); 

const env = process.env
const pool: Pool = mysql.createPool({
  host: env.MYSQL_HOST,
  port: Number(env.MYSQL_PORT),
  user: env.MYSQL_USER,
  password: env.MYSQL_PASSWORD,
  database: env.MYSQL_DATABASE,
})

/**
   * @description: 创建连接池，并返回query
   * @param {string} sql
   * @param {Object} options
   * @return {Promise}
*/
export const query = (sql: string, options?: QueryOptions): Promise<any> => {
  return new Promise((resolve, reject) => {
    pool.getConnection(function (err, conn: PoolConnection) {
      if (err) {
        reject(err)
      } else {
        conn.query(sql, options, function (err, result) {
          if (err) {
            reject(err)
          }
          conn.release()
          resolve(result)
        })
      }
    })
  })
}

export default pool;