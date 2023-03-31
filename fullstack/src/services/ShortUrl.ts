import {db, transform, env, getCacheClient} from "../helper"

export interface IShortUrlData {
    id: number
    url: string
    short: string
}

const isValidUrl = (url: string) => {
    try {
        new URL(url);
        // todo 更多校验规则
    } catch (_) {
        return false;
    }
    return true;
}

export default class ShortUrl {
    static PREFIX = env('SHORT_URL_PREFIX', "https://domain.com/")

    // 正常数据缓存时间
    static CACHE_LIFT_TIME = 60 * 60 * 24
    // 错误数据缓存时间
    static CACHE_LIFT_TIME_ERROR = 60 * 30

    // 完整连接
    public static completeUrl(short: string) {
        return `${this.PREFIX}${short}`
    }

    // 缓存key
    public static cacheKey(id: number) {
        return `short_url:${id}`
    }

    // 读取缓存
    private static async readCache(id: number) {
        const cache = await getCacheClient()
        const result = await cache.get(this.cacheKey(id))
        if (result !== null) {
            return JSON.parse(result) as IShortUrlData
        }
        return null
    }

    // 写入缓存
    private static async writeCache(data: IShortUrlData) {
        const cache = await getCacheClient()
        await cache.setEx(
            this.cacheKey(data.id),
            data.url === "" ? this.CACHE_LIFT_TIME_ERROR : this.CACHE_LIFT_TIME,
            JSON.stringify(data)
        )
    }

    // 更新缓存过期时间
    private static async updateCacheExpire(data: IShortUrlData) {
        const cache = await getCacheClient()
        await cache.expire(this.cacheKey(data.id), this.CACHE_LIFT_TIME)
    }

    // 创建短连接
    public static create(url: string) {
        return new Promise<IShortUrlData>((resolve, reject) => {
            if (!isValidUrl(url)) {
                return reject("Invalid url")
            }
            db((dbConnection) => {
                dbConnection.beginTransaction(() => {
                    dbConnection.query("INSERT INTO short_url (url) VALUES (?)", [url], async (err, result) => {
                        try {
                            if (err) {
                                // todo 记录日志
                                throw new Error(err.message)
                            }
                            const id = parseInt(result.insertId)

                            const short = transform.encode(id)

                            const shortData: IShortUrlData = {
                                id: id,
                                url,
                                short
                            }
                            await this.writeCache(shortData)
                            return dbConnection.commit(() => resolve(shortData));
                        } catch (_) {
                            // todo 记录日志
                            return dbConnection.rollback(() => {
                                return reject("Create short url failed 02")
                            })
                        }
                    })
                })
            })

        })
    }

    // 获取短连接
    public static get(short: string) {
        return new Promise<IShortUrlData>(async (resolve, reject) => {
            try {
                const id = transform.decode(short)
                const cacheData = await this.readCache(id)
                if (cacheData !== null) {
                    // 延长过期时间
                    await this.updateCacheExpire(cacheData)
                    return cacheData.url === "" ? reject("Invalid short url 02") : resolve(cacheData)
                }
                db((dbConnection) => {
                    dbConnection.query("SELECT `url` FROM short_url WHERE id = ? limit 1", [id], async (err, result) => {
                        const shortData: IShortUrlData = {
                            id: id,
                            url: result.length === 0 ? "" : result[0].url,
                            short: result.length === 0 ? "" : short
                        }
                        // 正常数据和异常数据均写入缓存
                        await this.writeCache(shortData)
                        return result.length === 0 ? reject("Invalid short url 03") : resolve(shortData)
                    })
                })
            } catch (_) {
                return reject("Invalid short url 01")
            }
        })
    }
}
