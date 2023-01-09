/**
 * @author: stella2fish
 */
import express from "express"
import bodyParser from "body-parser"

import UrlShorten from "./application/urlShorten"
import initDB from "./db/init"
import initRedis from "./redis/init"
import RequestWithBody from "./model/request"
import * as rateLimiter from "./middleware/rateLimiter"
import * as errorHandler from "./middleware/errorHandler"
import logger from "./lib/logger"

async function main() {
    const port = process.env.PORT || 3001

    const server = express()

    server.use(bodyParser.json())
    server.use(bodyParser.urlencoded({ extended: true }))

    // 初始化 DB 和 Redis 连接
    const dbManager = await initDB()
    const redisCli = await initRedis()

    // 初始化每台 Server 中 Redis 的发号段，不同的 Server 节点中 Redis 独立
    const business = new UrlShorten(dbManager, redisCli)
    await business.initCache()

    // API：通过短链接获取长链接
    server.get(
        "/v1/api/fetchLongByShort/:shortUrl",
        rateLimiter.fetchUrlLimiter,
        async (req: express.Request, res: express.Response) => {
            const value = await business.fetchLongByShort(req.params.shortUrl)
            res.send(value)
        }
    )

    /**
     * @description API：由长链接生成短链接
     * @param longUrl [string] 输入的长链接
     */
    server.post(
        "/v1/api/shorten",
        rateLimiter.shortenLimiter,
        async (req: RequestWithBody, res: express.Response) => {
            const value = await business.shorten(req.body.longUrl)
            res.send(value)
        }
    )

    // 全局异常处理
    server.use(errorHandler.notFound)

    server.listen(port, () => {
        logger.info(`The server is running on ${port}`)
    })
}

main()
