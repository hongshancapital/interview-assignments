// 主数据库
const PoolMainDbConnect = {
  // connectionLimit: 1,
  host: "localhost",
  port: 3306,
  username: process.env.MYSQL_USERNAME,
  password: process.env.MYSQL_PASSWORD,
  database: "tEgg",
  type: "mysql",
  logging: false,
  entities: [__dirname + "/../entity/*{.js,.ts}"]
}
const MysqlTable = {
  Sl_App: "sl_app",
  Sl_Short_Link: "sl_short_link"
}

export default { MysqlTable, PoolMainDbConnect }
