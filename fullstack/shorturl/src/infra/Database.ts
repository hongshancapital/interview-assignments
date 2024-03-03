import { createPool, PoolOptions, Pool, ResultSetHeader, RowDataPacket, OkPacket } from 'mysql2/promise';

import { Helper } from "./Helper";

export default class Database {
  /**
   * 数据库连接池实例
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @public
   */
  public static pool: Pool;

  /**
   * 初始化数据库
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param options - 配置选项
   *
   * @returns 返回数据库连接池
   */
  public static init(options: PoolOptions) {
    if (!this.pool)
      this.pool = createPool({
        waitForConnections: true,
        connectionLimit: 10,
        queueLimit: 0,
        namedPlaceholders: true,
        ...options,
      });

    return this.pool;
  }

  /**
   * 执行 SQL 语句
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param sql - SQL 语句
   * @param params - SQL 需要的参数
   *
   * @example
   *
   * Database.exec(`show databases`);
   * // => [rows, fields]
   *
   * @returns 返回数据库执行结果
   */
  public static async exec<T extends
      | RowDataPacket[][]
    | RowDataPacket[]
    | OkPacket
    | OkPacket[]
    | ResultSetHeader>(sql: string, params?: any | any[] | Record<string, any>) {
    return this.pool.execute<T>(sql, params);
  }

  /**
   * 查找一条数据
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param sql - SQL 语句
   * @param params - SQL 需要的参数
   *
   * @example
   *
   * Database.findOne(`show databases`);
   * // => row
   *
   * @returns 返回查找到的数据
   */
  static async findOne<T extends RowDataPacket>(sql: string, params?: any | any[] | Record<string, any>): Promise<T> {
    const [rows] = await this.exec<T[]>(sql, params);
    return Helper.head(rows);
  }

  /**
   * 查找多条数据
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param sql - SQL 语句
   * @param params - SQL 需要的参数
   *
   * @example
   *
   * Database.find(`show databases`);
   * // => [row, row]
   *
   * @returns 返回查找到的数据
   */
  static async find<T extends RowDataPacket[]>(sql: string, params?: any | any[] | Record<string, any>): Promise<T> {
    const [rows] = await this.exec<T>(sql, params);
    return rows;
  }

  /**
   * 插入一条数据
   *
   * @author microld
   *
   * @version 1.0.0
   *
   * @param sql - SQL 语句
   * @param params - SQL 需要的参数
   *
   * @returns 返回插入的数据
   */
  static async insert(sql: string, params?: any | any[] | Record<string, any>): Promise<number | null> {
    const [result] = await this.exec<ResultSetHeader>(sql, params);
    if (result.fieldCount === 0) return result.insertId;
    return null;
  }
}

