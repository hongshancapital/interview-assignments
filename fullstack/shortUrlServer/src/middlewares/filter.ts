import { Injectable, NestMiddleware, Query } from '@nestjs/common';
import { Response, Request } from 'express';
import { transformReqShortUrl } from '../utils';
import { BloomFilterService } from '../services/bloomFilter.service'
import { ERROR } from '../constants'

@Injectable()
export class filterMiddleware implements NestMiddleware {
    constructor(
        private readonly bloomFilterService: BloomFilterService,
    ) { }
    async use(req: Request, res: Response, next: Function) {
        const method = req.method
        const query = req.query

        if ((method === 'get' || method === 'GET') && query) {
            const { shortUrl } = query
            if (!shortUrl) {
                // get请求 不存在shortUrl 则报错
                throw new Error(JSON.stringify({
                    ...ERROR.INVALID_SHORTURL,
                    message: 'please set the shortUrl value in url query'
                }));
            } else {
                // bloomFilter 检查
                console.log('shortUrl', shortUrl)

                const { hostname, pathnameValue } = transformReqShortUrl(shortUrl)

                // shortUrl host 与 req host 不一致
                if (hostname !== req.hostname) {
                    throw new Error(JSON.stringify({
                        ...ERROR.INVALID_SHORTURL,
                        message: 'the shortUrl hostname is different from req hostname'
                    }))
                }
                // pathname长度一定大于1 （存在一位hash位）
                if (pathnameValue.length <= 1) {
                    throw new Error(JSON.stringify({
                        ...ERROR.INVALID_SHORTURL,
                        message: 'the shortUrl pathname length must more than 2'
                    }))
                }
                console.log('pathnameValue', pathnameValue)
                let isMayExistInDb = true
                try {
                    isMayExistInDb = await this.bloomFilterService.hasItemInBloomFilter(pathnameValue)
                } catch (e) {
                    console.log('查询 bloomfilter 异常', e.message)
                }
                console.log('isMayExistInDb', isMayExistInDb)
                if (isMayExistInDb) {
                    next()
                    return
                } else {
                    // 不存在则报错
                    throw new Error(JSON.stringify({
                        ...ERROR.INVALID_SHORTURL,
                        message: 'shortUrl is not exist in DB'
                    }))
                }
            }
        }

        next();
    }
}
