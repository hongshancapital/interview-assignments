/**
 * @description 检测待进行短链接转换的长链接地址是否合法
 * @param longUrl 长链接地址
 * @returns boolean 类型
 */
export function isValidWS(longUrl: string): boolean {
    let exp = /^http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/
    let objExp = new RegExp(exp)
    return objExp.test(longUrl)
}

/**
 * @description 检测短链接是否合法
 * @param shortUrl 短链接地址
 * @returns boolean 类型
 */
export function isValidShortUrl(shortUrl: string): boolean {
    let exp = /^[a-zA-Z0-9]{1,8}$/
    let objExp = new RegExp(exp)
    return objExp.test(shortUrl)
}
