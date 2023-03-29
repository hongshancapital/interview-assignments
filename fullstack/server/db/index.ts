import { query as mysqlQuery } from './mysql'
import { getValue as getValueFromRedis, setValue} from './redis'
import {NextFunction} from 'express'
export * from './sql'

// 统一编码处理方式
export const translateFunc: ITranslateFunc = {
  encode: encodeURI,
  decode: decodeURI,
}

/**
   * @description: 请求数据，先访问redis是否命中
   *               未命中，请求mysql，如果mysql存在，返回数据，并更新到redis中
   *               若mysql不存在，直接返回不存在
   * @param {string} sql
   * @param {string} val 
   * @param {NextFunction} next
   * @return {Promise<IResult[]>}
*/
export const getValue = async (sql: string, val: string, next: NextFunction): Promise<IResult| null> => {
  const enCodeVal = translateFunc.encode(val);
  let result = await getValueFromRedis(enCodeVal)
  if (!result) {
    const mysqlResult: IResult[] = await mysqlQuery(sql)
    if (mysqlResult?.length) {
      await setValue(enCodeVal, mysqlResult[0].long_link)
      return {
        long_link: translateFunc.decode(mysqlResult[0].long_link),
        short_link: translateFunc.decode(mysqlResult[0].short_link)
      };
    }
    return null;
  }
  return { long_link: translateFunc.decode(result), short_link: val};
}

/**
   * @description: 存数据，先保存数据库，再更新redis
   * @param {string} sql
   * @param {string} short_link 
   * @param {string} long_link 
   * @param {NextFunction} next
   * @return {Promise<IResult[]>}
*/
export const setValues = async (sql: string, short_link: string, long_link: string, next: NextFunction): Promise<IResult> =>{
  const res: [IResult] = await mysqlQuery(sql);
  if (res) {
    await setValue(translateFunc.encode(short_link), translateFunc.encode(long_link))
  }
  return {short_link, long_link}
}

export default { getValue, setValues }