import logger from './logger'

const MAX_URL_LENGTH = 1024
const DIGITS = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
const DOMAIN = 'http://www.test.com/'
const MAX_HASH_LENGTH = 8

/**
 * 统一封装 http 响应格式
 * @param data  返回结果
 * @param status 响应状态：0 成功，-1 出错
 * @param msg 出错信息
 */
export function createResponse(data: any, status = 0, msg: any = 'success') {
  const response = { msg, status }
  if (data) {
    // @ts-ignore
    response.data = data
  }
  return response
}

/**
 * 校验 str 是否是有效的 url
 * @param str
 */
export function validateUrl(str: string): boolean {
  if (!str || typeof str !== 'string') {
    return false
  }
  if (str.length > MAX_URL_LENGTH) {
    logger.error(`get too long url: ${str}`)
    return false
  }

  try {
    new URL(str)
    return true
  } catch (e) {
    return false
  }
}

/**
 * 校验 str 是否是有效的本服务短 url
 * @param str
 */
export function validateShortUrl(str: string): boolean {
  return validateUrl(str) && str.startsWith(DOMAIN) && str.length <= DOMAIN.length + MAX_HASH_LENGTH
}

/**
 * 获取短网址对应的 path
 * @param str
 */
export function getShortPath(shortUrl: string): string {
  return shortUrl.slice(DOMAIN.length)
}

/**
 * 将10进制数转为62进制字符串
 * @param n
 */
export function to62Str(n: number): string {
  if (n === 0) {
    return '0'
  }
  let result = ''
  while (n > 0) {
    result = `${DIGITS[n % DIGITS.length]}${result}`
    n = Math.floor(n / DIGITS.length)
  }
  return result
}

/**
 * 拼接完整 url
 * @param shortPath
 */
export function getFullShortUrl(shortPath: string): string {
  return `${DOMAIN}${shortPath}`
}
