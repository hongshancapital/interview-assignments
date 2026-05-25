import {
  createResponse,
  getFullShortUrl,
  getShortPath,
  validateShortUrl,
  validateUrl,
} from '../util/common'
import { request, summary, tags, orderAll, body, query } from 'koa-swagger-decorator'
import logger from '../util/logger'
import { getLongUrlByShort, getShortUrlByLong, shortenUrlAndSave } from '../service/url'

const tag = tags(['url转换'])

@orderAll(1)
class UrlRouter {
  @request('post', '/shortenUrl')
  @body({
    longUrl: { type: 'string', required: true, description: '原始长网址' },
  })
  @summary('转换长网址为短网址')
  @tag
  static async getShortenUrl(ctx: any) {
    const { longUrl } = ctx.request.body
    logger.info(`get shorten request for:${longUrl}`)
    if (!validateUrl(longUrl)) {
      ctx.body = createResponse(null, -1, `invalid url: ${longUrl}`)
      return
    }
    let shortUrl = await getShortUrlByLong(longUrl)
    //如果之前已经转换过，直接返回
    if (shortUrl) {
      ctx.body = createResponse(getFullShortUrl(shortUrl))
      return
    }
    //尝试转换短网址并存库
    shortUrl = await shortenUrlAndSave(longUrl)
    if (shortUrl) {
      ctx.body = createResponse(getFullShortUrl(shortUrl))
      return
    }
    ctx.body = createResponse(null, -1, `cant shorten url: ${longUrl}`)
  }

  @request('get', '/queryLongUrl')
  @query({
    shortUrl: { type: 'string', required: true, description: '转换后的短网址' },
  })
  @summary('查询短网址对应的长网址')
  @tag
  static async getLongUrl(ctx: any) {
    const { shortUrl } = ctx.query
    logger.info(`get long url query for:${shortUrl}`)
    if (!validateShortUrl(shortUrl)) {
      ctx.body = createResponse(null, -1, `invalid url: ${shortUrl}`)
      return
    }
    const longUrl = await getLongUrlByShort(getShortPath(shortUrl))
    if (longUrl) {
      ctx.body = createResponse(longUrl)
      return
    }
    ctx.body = createResponse(null, -1, `invalid short url: ${shortUrl}`)
  }
}

export default UrlRouter
