import * as mysql from 'mysql'
import config from '../config'
import logger from '../utils/logger'
import util from '../utils/util'

const pool: mysql.Pool = mysql.createPool({
  ...config.database
})

function formatRes <T>(sql: string, res: any): T[] {
  if (/^select/i.test(sql.trim())) {
    const list = []
    res.forEach((item: object) => {
      list.push(util.toCamelObj(item))
    })
    return list
  } else {
    return res
  }
}
class PoolUtil {
  query<T> (sql: string, params?: any[]): Promise<T[]> {
    const beginTime = new Date().getTime()
    return new Promise((resolve, reject) => {
      pool.query(sql, params, (err, result) => {
        if (err) {
          logger.error(`执行sql错误(${(new Date().getTime() - beginTime) / 1000}s): `, err.sql)
          reject(err)
        } else {
          logger.info(`sql exec(${(new Date().getTime() - beginTime) / 1000}s)`, sql, params)
          resolve(formatRes<T>(sql, result))
        }
      })
    })
  }
  write (sql: string, params?: any[]): Promise<mysql.OkPacket> {
    const beginTime = new Date().getTime()
    return new Promise((resolve, reject) => {
      pool.query(sql, params, (err, result) => {
        if (err) {
          logger.error(`执行insert语句错误(${(new Date().getTime() - beginTime) / 1000}s)`, err.sql)
          reject(err)
        } else {
          logger.info(`sql insert(${(new Date().getTime() - beginTime) / 1000}s): `, sql, params)
          resolve(result)
        }
      })
    })
  }
  beginTransaction (): Promise<mysql.PoolConnection> {
    return new Promise((resolve, reject) => {
      pool.getConnection((err, connection: mysql.PoolConnection) => {
        if (err) {
          logger.error('获取数据库连接失败', err)
          reject(err)
          return
        }
        connection.beginTransaction(error => {
          if (error) {
            logger.error('开启事务失败', error)
            reject(error)
          } else {
            resolve(connection)
          }
        })
      })
    })
  }
  queryInTransaction<T> (connect: mysql.PoolConnection, sql: string, params?: Array<string|number>): Promise<T[]> {
    return new Promise((resolve, reject) => {
      const beginTime = new Date().getTime()
      connect.query(sql, params, (err, results) => {
        if (err) {
          logger.error(`事务中执行sql失败(${(new Date().getTime() - beginTime) / 1000}s)`, err.sql)
          reject(err)
        } else {
          logger.info(`query in transaction(${(new Date().getTime() - beginTime) / 1000}s)`, sql, params)
          resolve(formatRes(sql, results))
        }
      })
    })
  }
  writeInTransaction (connect: mysql.PoolConnection, sql: string, params?: any[]): Promise<mysql.OkPacket> {
    return new Promise((resolve, reject) => {
      const beginTime = new Date().getTime()
      connect.query(sql, params, (err, result) => {
        if (err) {
          logger.error(`事务中执行sql失败(${(new Date().getTime() - beginTime) / 1000}s)`, err.sql)
          reject(err)
        } else {
          logger.info(`insert in transaction(${(new Date().getTime() - beginTime) / 1000}s)`, sql, params)
          resolve(result)
        }
      })
    })
  }
  commit (connection: mysql.PoolConnection): Promise<void> {
    return new Promise((resolve, reject) => {
      connection.commit(err => {
        if (err) {
          logger.error('提交事务失败', err)
          reject(err)
        } else {
          connection.release()
          resolve()
        }
      })
    })
  }
  rollback (connection: mysql.PoolConnection): void {
    connection.rollback(() => {
      connection.release()
    })
  }
}
export default new PoolUtil()