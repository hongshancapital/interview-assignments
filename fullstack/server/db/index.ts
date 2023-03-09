import { query as mysqlQuery } from './mysql'
import { getValue as getValueFromRedis, setValue} from './redis'
import {NextFunction} from 'express'
export * from './sql'

/**
   * @description: 请求数据库接口，请求query接口，先查看redis，未命中，再查看mysql（redis请求未实现）
   * @param {string} sql
   * @param {NextFunction} next
   * @return {Promise<any[]>}
*/
// export const query = async function (sql: string, next: NextFunction ): Promise<any[]> {
//   try {
//     const res = await mysqlQuery(sql)
//     return [null, res]
//   } catch (err) {
//     next(err)
//     return [err, null]
//   }
// }


/**
   * @description: 请求数据，先访问redis是否命中
   *               未命中，请求mysql，如果mysql存在，返回数据，并更新到redis中
   *               若mysql不存在，直接返回不存在
   * @param {string} sql
   * @param {string} val 
   * @param {NextFunction} next
   * @return {Promise<Result[]>}
*/
export const getValue = async (sql: string, val: string, next: NextFunction): Promise<Result> => {
  const enCodeVal = encodeURIComponent(val);
  let result = await getValueFromRedis(enCodeVal)
  if (!result) {
    const mysqlResult = await mysqlQuery(sql)
    if (mysqlResult?.length) {
      await setValue(enCodeVal, mysqlResult[0].long_link)
    }
    return mysqlResult[0];
  }
  return { long_link: decodeURIComponent(result), short_link: val};
}

/**
   * @description: 存数据，先保存数据库，再更新redis
   * @param {string} sql
   * @param {string} short_link 
   * @param {string} long_link 
   * @param {NextFunction} next
   * @return {Promise<Result[]>}
*/
export const setValues = async (sql: string, short_link: string, long_link: string, next: NextFunction): Promise<Result> =>{
  const res: [Result] = await mysqlQuery(sql);
  if (res) {
    await setValue(encodeURIComponent(short_link), encodeURIComponent(long_link))
  }
  return {short_link, long_link}
}

export default { getValue, setValues }