import base62 from 'base62/lib/ascii'
import { RESPONSE_CODE } from '../consts/response-code'
import { errorHandler, isErrorResponse, masterKnex } from '../utils/knex'

const SHORT_LENGTH = 8
const MAX_LONG_LENGTH = 240
const MIN_SHORT_NUMBER = Math.pow(62, 7) //保证base62后符合8位长度
const MAX_SHORT_NUMBER = Math.pow(62, 8) - 1
const MAX_TIMESTAMP = new Date('2100').getTime() //4102444800000，为随机数留出取值空间
const TABLE_NAME = 't_shorturl'

export const generateShortId = () => {
  const t = Date.now() //时间戳
  //保证随机数>MAX_TIMESTAMP，使被编码的数字不会因为高并发时t值重复而碰撞；且>MIN_SHORT_NUMBER，保证8位长度
  const rnd =
    Math.trunc(
      Math.random() * (MAX_SHORT_NUMBER - MIN_SHORT_NUMBER - MAX_TIMESTAMP)
    ) +
    MAX_TIMESTAMP +
    MIN_SHORT_NUMBER
  const short = base62.encode(t + rnd)
  return short
}

export default class ShortUrl {
  //将长网址变为短网址
  static async encode(url_long = '') {
    if (!url_long) {
      return { code: RESPONSE_CODE.ERROR_PARAMS_EMPTY, message: '参数不能为空' }
    }
    if (url_long.length > MAX_LONG_LENGTH) {
      return { code: RESPONSE_CODE.ERROR_LENGTH, message: '参数长度超长' }
    }
    //命中db则返回
    const records = await masterKnex(TABLE_NAME)
      .select('*')
      .where({ url_long })
      .catch(errorHandler)
    if (!isErrorResponse(records) && records.length) {
      const { url_short, url_long } = records[0]
      return { code: RESPONSE_CODE.SUCCESS, data: { url_long, url_short } }
    }

    //生成短网址并入库
    const url_short = generateShortId()
    const res = await masterKnex(TABLE_NAME).insert({ url_short, url_long })
    return { code: RESPONSE_CODE.SUCCESS, data: { url_long, url_short } }
  }

  //根据短网址查询长网址
  static async decode(url_short = '') {
    if (!url_short) {
      return { code: RESPONSE_CODE.ERROR_PARAMS_EMPTY, message: '参数不能为空' }
    }
    if (url_short.length != SHORT_LENGTH) {
      return { code: RESPONSE_CODE.ERROR_LENGTH, message: '参数长度不匹配' }
    }
    return masterKnex(TABLE_NAME)
      .select('*')
      .where({ url_short })
      .then((data) => {
        if (data.length) {
          const { url_long, url_short } = data[0]
          return { code: RESPONSE_CODE.SUCCESS, data: { url_long, url_short } }
        } else {
          return {
            code: RESPONSE_CODE.ERROR_NO_MATCHED,
            message: '未找到符合条件的记录',
          }
        }
      })
      .catch(errorHandler)
  }
}
