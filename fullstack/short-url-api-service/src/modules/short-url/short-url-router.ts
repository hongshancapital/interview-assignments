import { Router } from 'express'
import biz from './short-url-biz'
import db from './short-url-db-service'
import ServerResponse from "../../responses/server-response"
import { requestHandler } from "../../utils/router-util"
import { processEnv } from '../../app-config'
import { ShortUrl } from '../../entity/ShortUrl'
const router: Router = Router()

router.get("/url/:short_url", requestHandler(async (req, res) => {
    const errorMsg = biz.validateGetUrlParams(req.params, processEnv.SHORT_URL_BASE!)
    if (errorMsg) {
        res.status(400).json(ServerResponse.ValidationError(errorMsg))
        return
    }
    const { short_url } = req.params
    const id = biz.getIdFromShortUrlString(short_url, processEnv.SHORT_URL_BASE!)
    const shortUrlObj = await db.getByid(id)
    res.status(200).json(ServerResponse.Success(shortUrlObj?.long_url))
})
)

router.post("/", requestHandler(async (req, res) => {
    const errorMsg = biz.validateCreateBody(req.body, ShortUrl.LONG_URL_MAX_LENGTH)
    if (errorMsg) {
        res.status(400).json(ServerResponse.ValidationError(errorMsg))
        return
    }
    const maxId = await db.maxId()
    const validatedMaxIdErrorMsg = biz.validateMaxId(maxId)
    if (validatedMaxIdErrorMsg) {
        throw validatedMaxIdErrorMsg
    }
    const { long_url } = req.body
    const gotShortUrlObj = await db.getByLongUrl(long_url)
    if (gotShortUrlObj) {
        res.status(200).json(ServerResponse.Success(biz.shortUrlStringFromObj(gotShortUrlObj, processEnv.SHORT_URL_BASE!)))
        return
    }
    const createdShortUrlObj = await db.create(long_url)
    res.status(200).json(ServerResponse.Success(biz.shortUrlStringFromObj(createdShortUrlObj, processEnv.SHORT_URL_BASE!)))
}),
)

export default router
