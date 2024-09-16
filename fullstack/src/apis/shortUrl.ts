import { check, validationResult } from 'express-validator'
import { withCache } from '../components/redis'
import env from '../config/env'

import shortUrlService from '../services/shortUrl'
import asyncHandler from '../utils/asyncHandler'

const createShortUrl = withCache(
  async (longUrl: string) => {
    const urlMapping = await shortUrlService.create(longUrl)
    return urlMapping._id
  },
  (longUrl) => `longUrl:${longUrl}`,
)
const getLongUrl = withCache(
  async (shortUrl: string) => {
    const key = shortUrl.substring(shortUrl.lastIndexOf('/') + 1)
    const urlMapping = await shortUrlService.getByKey(key)
    return urlMapping?.longUrl
  },
  (shortUrl) => `shortUrl:${shortUrl}`,
)
/**
 * 创建短链接接口
 * @route POST /api/short-url/create
 */
export const createHandler = asyncHandler(async (req, res) => {
  await check('longUrl', 'longUrl cannot be blank')
    .isLength({ min: 1 })
    .run(req)
  const errors = validationResult(req)
  if (!errors.isEmpty()) {
    return res.status(400).json(errors)
  }
  const { longUrl } = req.body
  const shortUrlKey = await createShortUrl(longUrl)
  return res.json({ shortUrl: `${env.baseDomain}/${shortUrlKey}` })
})

/**
 * 返回长链接
 * @route get /api/getLongUrl?shortUrl=:shortUrl
 */
export const getLongUrlHandler = asyncHandler(async (req, res) => {
  await check('shortUrl', 'shortUrl cannot be blank')
    .isLength({ min: 1 })
    .run(req)
  const errors = validationResult(req)
  if (!errors.isEmpty()) {
    return res.status(400).json(errors)
  }
  const shortUrl = req.query.shortUrl
  const longUrl = await getLongUrl(shortUrl as string)
  if (longUrl == null) {
    return res.status(404).end()
  }
  return res.json({ longUrl })
})
