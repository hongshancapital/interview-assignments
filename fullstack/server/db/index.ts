import { query as mysqlQuery } from './mysql'
import {NextFunction} from 'express'
export * from './sql'

/**
   * @description: 请求数据库接口，请求query接口，先查看redis，未命中，再查看mysql（redis请求未实现）
   * @param {string} sql
   * @param {NextFunction} next
   * @return {Promise<any[]>}
*/
export const query = async function (sql: string, next: NextFunction ): Promise<any[]> {
  try {
    const res = await mysqlQuery(sql)
    return [null, res]
  } catch (err) {
    next(err)
    return [err, null]
  }
}

export default { query }