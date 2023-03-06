import mysql, {Pool} from 'mysql'

const pool: Pool = mysql.createPool({
  host: 'localhost',
  port: 3306,
  user: "admin",
  password: 'password',
  database: 'INTERVIEW',
})

export const query = (sql: string, options: any) => {
  return new Promise((resolve, reject) => {
    pool.getConnection(function (err, conn) {
      if (err) {
        reject(err)
      } else {
        conn.query(sql, options, function (err, result, fields) {
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