import {
    Post,
    Body,
    Get,
    Query,
    Delete,
    Param,
    Patch,
    Request,
    UseGuards,
    Controller,
    UseInterceptors,
    Req
} from '@nestjs/common'
import _ from 'lodash'
import { ShortUrlMapService } from './shorturlmap.service'
import { CHARIN62, ERROR } from '../../constants'
import { HashService } from '../../services/hash.service'
import { calSingleCharSub, transformReqShortUrl } from '../../utils'
import { RedisService } from '../../services/redis.service'
import { BloomFilterService } from '../../services/bloomFilter.service'

interface ICreateShortUrlReq {
    longUrl: string
    expireTime?: number
}

interface IDeleteShortUrlMapReq {
    shortUrl?: string
}

interface IGetLongUrlRes {
    data: string
}

interface ICreateShortUrlRes {
    data: string
}

@Controller('shortUrl')
export class ShortUrlMapController {
    constructor(
        private readonly shortUrlService: ShortUrlMapService,
        private readonly hashServive: HashService,
        private readonly redisService: RedisService,
        private readonly bloomFilterService: BloomFilterService
    ) { }

    // 通过短链获取长链
    @Get()
    async getLongUrl(@Query() query: { shortUrl?: string } = {}): Promise<IGetLongUrlRes> {
        const { shortUrl } = query

        // 判断 shortUrl 是否存在
        if (!shortUrl) {
            throw new Error(JSON.stringify({
                ...ERROR.INVALID_SHORTURL,
                message: 'shortUrl not exist in url query!'
            }))
        }

        // 获取hashFlag, path
        const { pathnameValue, hashFlag, realShortUrl } = transformReqShortUrl(shortUrl)

        // 先请求redis 查询
        try {
            let longUrl: string = await this.redisService.get(pathnameValue)
            if (longUrl) {
                console.log('读redis 缓存')
                return {
                    data: longUrl
                }
            }
        } catch (e) {
            console.log('redis 读异常打印', e)
        }


        // 基于短链path，换算数据库_id
        const id = this.shortUrlToId(realShortUrl)
        const urlMapRes = await this.shortUrlService.queryShortUrlMap({
            id,
            flag: hashFlag
        })
        console.log('urlMapRes', urlMapRes)
        if (urlMapRes.length === 0) {
            return {
                data: ''
            }
        }

        return {
            data: urlMapRes[0].longUrl
        }
    }

    /**
     * 根据短链删除长短链映射
     * @param {IDeleteShortUrlMapReq} body
     * @memberof ShortUrlMapController
     */
    @Delete()
    async deleteShortUrlMap(@Body() body: IDeleteShortUrlMapReq): Promise<void> {
        const { shortUrl } = body

        // 判断 shortUrl 是否存在
        if (!shortUrl) {
            throw new Error(JSON.stringify({
                ...ERROR.INVALID_SHORTURL,
                message: 'shortUrl not exist in url query'
            }))
        }

        // 获取hashFlag, path
        const { pathnameValue, hashFlag, realShortUrl } = transformReqShortUrl(shortUrl)

        const res = await this.shortUrlService.deleteShortUrlMap({
            shortUrl: realShortUrl,
            flag: hashFlag
        })
        console.log('res', res)
        // 删除 redis 缓存
        try {
            await this.redisService.set(pathnameValue, '', 1)
        } catch (e) {
            console.log('redis 写异常打印', e)
        }
    }


    // 生成短链
    @Post()
    async createShortUrl(@Body() body: ICreateShortUrlReq, @Req() req): Promise<ICreateShortUrlRes> {
        console.log('body', body)
        const { longUrl, expireTime } = body

        // 判断 shortUrl 是否存在
        if (!longUrl) {
            throw new Error(JSON.stringify({
                ...ERROR.INVALID_LONGURL,
                message: 'longUrl not exist in req body!'
            }))
        }

        // 计算该长链对应一致性hash值 （0 ，1）
        const hashFlag = this.hashServive.getEnvByHashUrl(longUrl)
        // 检查longUrl对应短链 是否已存在
        const existUrlMap = await this.shortUrlService.queryShortUrlMap({
            longUrl,
            flag: hashFlag
        })
        console.log('existUrlMap', existUrlMap)
        // 存在则直接返回 shortUrl
        if (existUrlMap.length) {
            return {
                data: `http://${req.hostname}/${hashFlag}${existUrlMap[0].shortUrl}`
            }
        }

        const curTime = new Date()

        // 如果未设置过期时间，默认设置1天后过期
        let finalExpireTime
        if (expireTime) {
            finalExpireTime = new Date(expireTime)
        } else {
            finalExpireTime = new Date()
            finalExpireTime.setDate(finalExpireTime.getDate() + 1) // 默认设置1天后过期
        }

        // 先插入 longUrl 字段
        const insertId = await this.shortUrlService.createShortUrlMap({
            longUrl,
            flag: hashFlag,
            expireTime: finalExpireTime,
            createdTime: curTime
        })

        // 获取插入 DocId, 并转换为短链
        const realShortUrl = this.idToShortUrl(insertId)
        const pathnameValue = `${hashFlag}${realShortUrl}`
        const shortUrl = `http://${req.hostname}/${pathnameValue}`

        // 更新该行数据 shortUrl 字段
        await this.shortUrlService.updateShortUrlMapById({
            id: insertId,
            updateData: {
                shortUrl: realShortUrl
            },
            flag: hashFlag
        })

        // 更新redis
        try {
            await this.redisService.set(pathnameValue, longUrl, Math.floor((finalExpireTime.getTime() - curTime.getTime()) / 1000))
        } catch (e) {
            // 兜住redis 报错，放通请求 DB 即可
            console.log('redis 写异常打印', e)
        }

        // bloomFilter 添加元素
        try {
            this.bloomFilterService.addItemToBloomFilter(pathnameValue)
        } catch (e) {
            console.log('bloomFilter 添加元素报错', e)
        }

        return {
            data: shortUrl
        }
    }

    /**
     * docId 转 62进制 字符串（取值范围 abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789）
     * @private
     * @param {*} shortUrl
     * @returns {string}
     * @memberof ShortUrlMapController
     */
    private idToShortUrl(id: number): string {
        let shorturl: string[] = [];

        // Convert given integer id to a base 62 number
        while (id) {
            shorturl.unshift(CHARIN62[id % 62]);
            id = Math.floor(id / 62);
        }
        return shorturl.join('');
    }

    private shortUrlToId(shortUrl: string): number {
        let id = 0; // initialize result

        // A simple base conversion logic
        for (let i = 0; i < shortUrl.length; i++) {
            if ('a' <= shortUrl[i] && shortUrl[i] <= 'z')
                id = id * 62 + calSingleCharSub(shortUrl[i], 'a');
            if ('A' <= shortUrl[i] && shortUrl[i] <= 'Z')
                id = id * 62 + calSingleCharSub(shortUrl[i], 'A') + 26;
            if ('0' <= shortUrl[i] && shortUrl[i] <= '9')
                id = id * 62 + calSingleCharSub(shortUrl[i], '0') + 52;
        }
        return id;
    }
}
