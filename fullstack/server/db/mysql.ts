import mysql, {Pool} from 'mysql'

const pool: Pool = mysql.createPool({
  host: 'localhost',
  user: 'admin',
  password: 'password',
  database: ''
})

export const query = (sql, options, callback) => {
  pool.getConnection(function (err, conn) {
    if (err) {
      callback(err, null, null);
    } else {
      conn.query(sql, options, function (err, result, fields) {
        conn.release()
        callback(err, result, fields)
      })
    }
  })
}

export default pool;