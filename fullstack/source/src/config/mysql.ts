import mysql, { Connection, MysqlError } from 'mysql';
import log4js from "log4js";

const logger = log4js.getLogger("app")
class Mysql {
  // 每用到一次，打开一个数据库连接，需要有池化技术进行优化和控制
  private getConnection(): Connection {
    return mysql.createConnection({
      host: 'localhost',
      user: 'root',
      password: '8fangshuo',
      port: 3306,
      database: 'bafangshuo',
    });
  }

  // promise封装
  private query(conn: Connection, sql: string, params?: any[]) {
    if (!conn) {
      return;
    }
    return new Promise((resolve, reject) => {
      conn.query(sql, params, (err: MysqlError | null, data: any) => {
        // 关闭数据库连接
        conn.end()
        if (err) {
          logger.error(err);
          reject(err);
        } else {
          resolve(data);
        }
      });
    });
  }

  async insert(hostMap: HostMap): Promise<number> {
    let connection = this.getConnection()
    let data: any = await this.query(connection, "insert into host_map (host_long) values(?)", [hostMap.host_long])
    if (data && data.affectedRows) {
      return data.insertId
    }
    return null
  }

  async selectByShortHost(shortHost: string): Promise<HostMap> {
    let connection = this.getConnection()
    let data: any = await this.query(connection, "select * from host_map where host_short = ?", [shortHost])
    if (data && data.length > 0) {
      return data[0]
    }
    return null
  }

  async selectByLongHost(longHost: string): Promise<HostMap> {
    let connection = this.getConnection()
    let data: any = await this.query(connection, "select * from host_map where host_long = ?", [longHost])
    if (data && data.length > 0) {
      return data[0]
    }
    return null
  }

  async updateShortHost(hostMap: HostMap) {
    let connection = this.getConnection()
    await this.query(connection, "update host_map set host_short = ? where id = ?", [hostMap.host_short, hostMap.id])
  }
}
export default new Mysql()
