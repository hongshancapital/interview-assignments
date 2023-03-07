import mysql, {Pool, PoolConnection} from 'mysql'

const pool: Pool = mysql.createPool({
  host: 'localhost',
  port: 3306,
  user: "admin",
  password: 'password',
  database: 'INTERVIEW',
})

export const query = (sql: string, options: any) => {
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