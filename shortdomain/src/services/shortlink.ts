import Url from '../libs/url'
import cache from '../libs/cache'
import flag from '../libs/flag'
import model from '../models/shortlink'

async function getFlagByUrl(url: Url): Promise<string> {
    // 先查 Bloom 过滤器
    if (!await cache.isUrlInBloom(url)) {
        return ''
    }

    // bloom 过滤器显示可能存在，查数据库
    const shortLink = await model.getByUrl(url)

    return shortLink ? shortLink.flag : ''
}

async function genFlagForUrl(url: Url): Promise<string> {
    // 插入数据库
    const shortLink = await model.add(url, flag.CURR_VERTION)
    // 保存到 cache 中
    await cache.add(shortLink.flag, shortLink.url)

    return shortLink.flag
}

/**
 * 为 url 生成短链接
 * 如 https://www.mydomain.com/user/info?a=1&b=2&c=3 转成短域名 https://www.d.cn/ab7Hu，
 * 其中 ab7Hu 就是要生成的 flag
 * 
 * @param urlStr - 合法的长链接 url
 * @returns 短链接的 flag
 */
async function genShortLink(urlStr: string): Promise<string> {
    const url = new Url(urlStr)

    // 如果已经存在则直接返回
    const flag = await getFlagByUrl(url)
    if (flag) {
        return flag
    }

    // 生成新 flag 并返回
    return await genFlagForUrl(url)
}

/**
 * 根据短链接 flag 查询并返回对应的长链接 url
 * 
 * @param flg - 短链接 flag
 * @returns 长链接 url
 */
async function getUrl(flg: string): Promise<string> {
    const id = flag.flag2id(flg)

    // 先从缓存查
    const cResult = await cache.get(flg)
    if (cResult.cacheVal) {
        return cResult.cacheVal
    } else if (!cResult.maybeExists) {
        // 一定不存在
        return ''
    }

    // 从数据库查
    const dResult = await model.getById(id)

    return dResult ? dResult.url.url : ''
}

export default { genShortLink, getUrl }