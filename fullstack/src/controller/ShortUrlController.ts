import * as assert from "assert"
import { NextFunction, Request, Response } from "express"
import { ShortUrlMap } from "../entity/ShortUrlMap"
import { shortUrlMock, testData } from "../mock/ShortUrl.mock"
import { ShortUrlService } from "../service/shortUrlService"
import { shortUrlUtils } from "../utils/shortUrlUtils"

/**
 * 这里更像是一个集成测试，而不是一个单元测试
 */
export class ShortUrlController {
	constructor(
		// 这里考虑使用依赖注入
		private shortUrlService: ShortUrlService = new ShortUrlService()
	) {}

	// 根据长链接获取短链接
	public async longUrlToShortUrl(
		req: Request,
		res: Response,
		next: NextFunction
	) {
		const longUrl = req.body.longUrl
		if (!longUrl) {
			res.status(200).send({ error: 400, message: "缺少参数longUrl" })
			return
		}

		if (!shortUrlUtils.isValidUrl(longUrl)) {
			res.status(200).send({
				error: 400,
				message: "长链接不合法",
			})
			return
		}

		// 保存短链接
		const shortUrl = await this.shortUrlService.longToShortUrl(longUrl)
		assert(shortUrl, "获取长链接失败")
		res.send({
			shortUrl: shortUrl.shortUrl,
			longUrl,
			message: "success",
		})
	}

	// 根据短链接获取长链接
	public async getLongUrl(req: Request, res: Response, next: NextFunction) {
		const shortUrl = req.query.shortUrl as string
		if (!shortUrl) {
			res.status(200).send({ error: 400, message: "缺少参数 shortUrl" })
			return
		}
		if (!shortUrlUtils.isValidUrl(shortUrl)) {
			res.status(200).send({
				error: 400,
				message: "链接不合法",
			})
			return
		}
		// 判断短链接是否存在长链接
		const exitsLongUrl = await this.shortUrlService.getLongUrlByShortUrl(
			shortUrl as string
		)

		// 生成短链接
		if (exitsLongUrl) {
			res.send({
				shortUrl: shortUrl,
				longUrl: exitsLongUrl,
				message: "success",
			})
		} else {
			res.status(200).send({ error: 400, message: "获取短链接失败" })
		}
	}
}
