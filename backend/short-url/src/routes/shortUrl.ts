import { Request, Response } from 'express'
import { getLongUrl, getShortUrl } from '../services/url'

const shortUrl = {
  async getLongUrl(req: Request, res: Response) {
    try {
      let domain = await getLongUrl(req.params.shortUrl)
      res.json(domain)
    } catch (err) {
      res.status(500)
    }
  },
  async getShortUrl(req: Request, res: Response) {
    try {
      console.log(req.body)
      const result = await getShortUrl(req.body.url)
      // console.log(result)
      if (result.length == 0) {
        res.json({ code: 404, result: 'not found' })
      } else {
        // console.log(result)
        res.json(result)
      }
    } catch (err) {
      res.status(500)
    }
  },
}

export default shortUrl
