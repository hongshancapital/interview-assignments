import { ERROR } from '../constants'

/**
 * 计算2个单字符减法
 *
 * @export
 */
export function calSingleCharSub(a: string, b: string) {
    return a.charCodeAt(0) - b.charCodeAt(0)
}

export function transformReqShortUrl(originShortUrl: string): {
    pathnameValue: string // 获取 url path中 / 之后的字符串
    hashFlag: string // hash值（映射DB资源）
    realShortUrl: string // DB 中存储的值
    hostname: string
} {
    try {
        const url = new URL(originShortUrl)
        const pathname = url.pathname
        const pathnameValue = pathname.substring(1) // redis 中存储 pathnameValue
        const hashFlag = pathnameValue[0]
        const realShortUrl = pathnameValue.substring(1)
        const hostname = url.hostname
        return {
            pathnameValue,
            hashFlag,
            realShortUrl,
            hostname
        }
    } catch (e) {
        throw new Error(JSON.stringify({
            ...ERROR.INVALID_SHORTURL,
            message: e.message
        }))
    }

}