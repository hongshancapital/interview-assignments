/**
 * urlShortToLong.ts
 * @authors lizilong
 * @description 接收短域名并自动重定向至长域名的接口API
 */

import epxress from 'express'
const router = epxress.Router()
import Url from '../models/url'

router.get('/:code', async (req, res, next) => {
	try {
		const urlCode = req.params.code
		// 判断短域名在MongoDB数据库中是否存在
		const url = await Url.findOne({ urlCode })
		if (url) {
			// 库中存在则取出后直接重定向至原链接
			res.redirect(url.longUrl)
		} else {
			// 库中不存在则给出不存在提示
			res.status(404).json({
				status: '20001',
				msg: 'No url found',
			})
		}
	} catch (error) {
		res.status(500).json({
			status: '20002',
			msg: 'Server error',
		})
	}
})

export default router
