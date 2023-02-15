import mysql, {Connection} from 'mysql';

const conn:Connection = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: "root",
  database: "tinyurl"
});

conn.connect();

export default conn;