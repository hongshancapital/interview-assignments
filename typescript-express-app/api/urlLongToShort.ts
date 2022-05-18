import epxress from 'express'
import Url from '../models/url'
import tool from '../utils/tool'
const router = epxress.Router()
const baseUrl: string = "http://localhost:3000"
router.post('/', async (req, res, next) => {
	const { longUrl } = req.body
	// 判断传过来的长Url是否合法
	if (tool.checkUrl(longUrl)) {
		try {
			// 判断传过来的长Url在MongoDB数据库中是否已存在
			let url = await Url.findOne({ longUrl })
			if (url) {
				// 库中存在则取出后短域名后直接返回
				res.json({
					status: '0',
					msg: 'ok',
					shortUrl: `${baseUrl}/${url.urlCode}`,
				})
			} else {
				// 库中不存在则生成长度为8个字符的短域名
				let urlCode = tool.shortUrlGenerator()
				url = new Url({
					longUrl,
					urlCode,
				})
				// 将这条新记录存入MongoDB数据库
				await url.save()
				res.json({
					status: '0',
					msg: 'ok',
					shortUrl: `${baseUrl}/${urlCode}`,
				})
			}
		} catch (error) {
			res.status(500).json({
				status: '10001',
				msg: 'Server error',
			})
		}
	} else {
		res.status(401).json({
			status: '10002',
			msg: 'Invalid long url',
		})
	}
})
export default router
