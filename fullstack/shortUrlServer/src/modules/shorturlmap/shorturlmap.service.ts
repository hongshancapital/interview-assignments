import { Injectable } from '@nestjs/common'
import { InjectRepository, InjectEntityManager, InjectConnection } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { ShortUrlMap } from './shorturlmap.entity';


export interface IShortUrlMap {
    shortUrl: string
    longUrl: string
    createTime: string
}

@Injectable()
export class ShortUrlMapService {

    constructor(
        @InjectRepository(ShortUrlMap, 'primary')
        private readonly primaryShortUrlMapRepository: Repository<ShortUrlMap>,
        @InjectRepository(ShortUrlMap, 'backup')
        private readonly backUpShortUrlMapRepository: Repository<ShortUrlMap>,
    ) { }
    /**
     * 存入长链与短链的映射关系
     * @param shortUrl
     * @param longUrl
     * @returns
     */
    async createShortUrlMap(params: {
        flag: string,
        longUrl?: string,// 插入记录时，短链信息并不存在，需先插入长链字段在换算短链字段并更新
        expireTime?: Date,
        createdTime?: Date
    }): Promise<number> {
        let { flag, longUrl = '', expireTime, createdTime } = params

        const insertRes = await this.getDbByFlag(flag).insert({
            longUrl,
            createdTime,
            expireTime,
            shortUrl: ''
        })
        const id = insertRes.raw.insertId
        return id
    }

    /**
     * 更新长短链映射字段
     * @param {{
     *         id: string,
     *         updateData: {
     *             shortUrl?: string,
     *             longUrl?: string
     *         },
     *         envId: string
     *     }} params
     * @returns {Promise<void>}
     * @memberof ShortUrlMapService
     */
    async updateShortUrlMapById(params: {
        id: number,
        updateData: {
            shortUrl?: string,
            longUrl?: string
        },
        flag: string
    }) {
        const { id, updateData, flag } = params
        return this.getDbByFlag(flag).update({
            id
        }, { ...updateData })
    }

    /**
     * 查询长链与短链的映射关系
     * @param shortUrl
     * @param longUrl
     * @returns
     */
    async queryShortUrlMap(params: {
        shortUrl?: string,
        longUrl?: string,
        id?: number
        flag?: string
    }) {
        const { shortUrl, longUrl, id, flag } = params
        let findParams: any = {}
        if (shortUrl) {
            findParams.shortUrl = shortUrl
        }
        if (longUrl) {
            findParams.longUrl = longUrl
        }
        if (id !== undefined) {
            findParams.id = id
        }
        console.log('findParams', findParams)
        return this.getDbByFlag(flag).find(findParams)
    }

    /**
     * 删除长短链映射
     * @param {string} shortUrl
     * @returns {Promise<void>}
     * @memberof ShortUrlMapService
     */
    async deleteShortUrlMap(params: {
        shortUrl?: string,
        flag?: string
    }) {
        const { shortUrl, flag } = params
        const reqParams: any = {}
        if (shortUrl) {
            reqParams.shortUrl = shortUrl
        }

        return this.getDbByFlag(flag).delete(reqParams)
    }

    /**
     * 0 主 1 备
     * @param {string} flag
     * @returns
     * @memberof ShortUrlMapService
     */
    getDbByFlag(flag: string): Repository<ShortUrlMap> {
        console.log('flag', flag)
        return flag === '1' ? this.backUpShortUrlMapRepository : this.primaryShortUrlMapRepository
    }
}
