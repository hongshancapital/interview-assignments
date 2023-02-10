/**
 * @todo 需要增加鉴权中间件
 */
import express, { NextFunction, Request, Response } from 'express'
import svr from '../services/shortlink'
import resp from '../libs/res'
import ErrCode from '../config/code'

const router = express.Router()

/**
 * 根据短域名（code）查询对应的长域名
 * 入参：
 *     flag {string} - 短域名 code
 * 返回：
 *     存在：{ code: 200, data: { url: '...' }, message: '' }
 *     不存在：{ code: 404, data: {}, message: 'not found' }
 */
router.get('/:flag', async (req: Request, res: Response, next: NextFunction) => {
    try {
        const flag = req.params.flag
        if (!flag) {
            return resp.jsonFail(res, '缺少参数: flag', ErrCode.INVALID_PARAMS)
        }

        const url = await svr.getUrl(flag as string)

        if (url) {
            resp.jsonOK(res, { url })
        } else {
            resp.jsonFail(res, '未找到 url', ErrCode.NOT_FOUND)
        }
    } catch (e) {
        next(e)
    }
})

/**
 * 根据长域名生成短域名并返回
 * 如果对应的长域名已经生成过短域名则直接返回
 * 如果提供的长域名不是合法的 url，则返回错误
 * 入参：
 *     url {string} - 合法的长域名（链接）
 * 返回：
 *     成功：{ code: 200, data: { short: '...' }, message: '' }
 *     失败：{ code: 500, data: {}, message: '失败原因' }
 */
router.post('/', async (req: Request, res: Response, next: NextFunction) => {
    try {
        const url = req.body.url
        if (!url) {
            return resp.jsonFail(res, '缺少参数: url', ErrCode.INVALID_PARAMS)
        }

        resp.jsonOK(res, { short: await svr.genShortLink(decodeURIComponent(url)) })
    } catch (e) {
        next(e)
    }
})

export default router
