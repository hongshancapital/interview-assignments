import express from 'express'
import shortUrl from './shortUrl'

const router = express.Router()

// 短域名从url中传递更方便
router.route('/getLongUrl/:shortUrl').get(shortUrl.getLongUrl)
// 长域名属于真实域名，并且URL长度有限制，使用POST提交更安全
router.route('/getShortUrl').post(shortUrl.getShortUrl)

export default router
