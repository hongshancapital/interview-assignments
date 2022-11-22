import { ShortLinkRepository, IShortLink, createShortLink } from './db';
import { BloomFilter } from './bloomfilter';
import { Cache } from './cache';
import { isURL, encodeID, decodeID, splitURL, delay } from './utils';

export class Service {
    private cache: Cache
    private bf: BloomFilter
    private slr: ShortLinkRepository

    constructor(cache: Cache, bf: BloomFilter, slr: ShortLinkRepository) {
        this.cache = cache
        this.bf = bf
        this.slr = slr
    }

    async urlHash(url: string): Promise<string | number> {
        const [domain, path] = splitURL(url)
        let shortlink: IShortLink | undefined
        if (await this.bf.urlExist(url)) {
            shortlink = await this.slr.findByUrl(domain, path)
            if (shortlink) {
                return shortlink.hash!
            } else {
                // 可能存在，找不到新创建
                shortlink = await this.findOrCreateWithLock(url)
            }
        } else {
            shortlink = await this.findOrCreateWithLock(url)
        }
        if (shortlink) {
            //创建成功
            await this.bf.hashAdd(shortlink.hash!)
            await this.bf.urlAdd(url)

            return shortlink.hash!
        } else {
            return -1
        }
    }

    private async findOrCreateWithLock(url: string): Promise<IShortLink | undefined> {
        const [domain, path] = splitURL(url)
        let lv: string | null = null
        try {
            lv = await this.cache.tryLock(url)
            if (lv) {
                return await this.findOrCreate(domain, path)
            } else {
                while (lv == null) {
                    await delay(500)
                    lv = await this.cache.tryLock(url)
                }
                return await this.findOrCreate(domain, path)
            }
        } finally {
            await this.cache.releaseLock(url, lv!)
        }
    }

    private async findOrCreate(domain: string, path: string): Promise<IShortLink | undefined> {
        let shortlink = await this.slr.findByUrl(domain, path)
        if (shortlink) {
            return shortlink
        } else {
            return this.newShortlink(domain, path)
        }
    }

    private async newShortlink(domain: string, path: string): Promise<IShortLink | undefined> {
        let isl: IShortLink | undefined = await this.slr.create(createShortLink({
            domain,
            path
        }))
        if (isl && isl.id) {
            isl.hash = encodeID(isl.id!)
            isl = await this.slr.update(isl)
        }
        return isl
    }

    async hashUrl(hash: string): Promise<string | number> {
        if (await this.bf.hashExist(hash)) {
            let url = await this.cache.get(hash)
            if (url) {
                return url
            } else {
                // 应对缓存击穿的场景这里需要互斥
                let lv: string | null = null
                let shortlink: IShortLink | undefined
                try {
                    let slid = decodeID(hash)
                    lv = await this.cache.tryLock(hash)
                    if (lv) {
                        shortlink = await this.slr.findById(slid)
                    } else {
                        while (lv == null) {
                            await delay(500)
                            shortlink = await this.slr.findById(slid)
                        }
                    }
                    const url = shortlink?.domain! + shortlink?.path!
                    this.cache.set(hash, url)
                    return url
                } catch (e) {
                    return -1
                } finally {
                    this.cache.releaseLock(hash, lv!)
                }
            }

        } else {
            return 0
        }
    }

}