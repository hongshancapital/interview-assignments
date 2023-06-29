import mysql from "mysql";

export const pool = mysql.createPool({
  connectionLimit: 10,
  host: "localhost",
  port: 3306,
  user: "root",
  password: "123456",
  database: "shortlink",
});
