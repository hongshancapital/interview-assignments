import { query as mysqlQuery } from './mysql'

export const query = async function (sql: string): Promise<any[]> {
  try {
    const res = await mysqlQuery(sql, {})
    return [null, res]
  } catch (err) {
    return [err, null]
  }
}

export const inject = async function (sql: string): Promise<any[]> {
  try {
    const res = await mysqlQuery(sql, {})
    return [null, res]
  } catch (err) {
    return [err, null]
  }
}

export default {query}