import mysql from 'mysql'
import util from 'util'

const conn = mysql.createConnection({
  host: '127.0.0.1',
  user: 'root',
  password: '12345678',
  port: 3306,
  database: 'domain',
  debug: true,
  // rowsAsArray: true,
})

conn.connect(function (err) {
  if (err) {
    console.error('error connecting: ' + err.stack)
    return
  }

  console.log('connected as id ' + conn.threadId)
})
// node native promisify
const query = util.promisify(conn.query).bind(conn)

export { conn, query }
