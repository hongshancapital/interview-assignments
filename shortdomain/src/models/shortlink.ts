/**
 * @todo 生产中需要实现分表策略
 */
import db from './db'
import Url from '../libs/url'
import flag from '../libs/flag'
import { InvalidNumberError } from '../errors'

class ShortLink {
    readonly status: number;

    constructor(readonly url: Url, readonly version: number, public id: number | null = null) {
        if (typeof id === 'number' && (id < 1 || id > flag.MAX_ID)) {
            throw new InvalidNumberError('非法的 id 值' + id)
        }

        if (version < 1 || version > flag.MAX_VERSION) {
            throw new InvalidNumberError('非法的版本号:' + version)
        }

        this.status = 1
    }

    get flag(): string {
        if (!this.id) {
            return ''
        }

        return flag.id2flag(this.id, this.version)
    }
}

/**
 * 根据长链接 url 查询短链信息
 * 
 * @param url - 长链接 url
 * @returns 如果存在则返回，如果不存在则返回 null
 */
async function getByUrl(url: Url): Promise<ShortLink | null> {
    const result = await db
                    .select(['id', 'version'])
                    .from('shortdomain')
                    .where({ crc32: url.crc32, url: url.url, status: 1 })
                    .first()

    return !result ? null : new ShortLink(url, result.version, result.id)
}

async function getById(id: number): Promise<ShortLink | null> {
    const result = await db
                    .select(['id', 'url', 'version'])
                    .from('shortdomain')
                    .where('id', id)
                    .andWhere('status', 1)
                    .first()

    return !result ? null : new ShortLink(new Url(result.url), result.version, result.id)
}

/**
 * 插入新 url 到数据库并生成 id
 * @param url - 长链接 url
 * @param version - 使用的 flag 生成算法版本号
 */
async function add(url: Url, version: number): Promise<ShortLink> {
    const result = await db
                    .table('shortdomain')
                    .insert({
                        url: url.url,
                        crc32: url.crc32,
                        version: version,
                        userid: 0,// 暂时写死占位
                    })

    return new ShortLink(url, version, result[0])
}

export { ShortLink }
export default { getByUrl, getById, add, ShortLink }