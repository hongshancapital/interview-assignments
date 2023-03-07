import { query as mysqlQuery } from './mysql'
import {NextFunction} from 'express'
export * from './sql'

export const query = async function (sql: string, next: NextFunction ): Promise<any[]> {
  try {
    const res = await mysqlQuery(sql, {})
    return [null, res]
  } catch (err) {
    next(err)
    return [err, null]
  }
}

export default { query }