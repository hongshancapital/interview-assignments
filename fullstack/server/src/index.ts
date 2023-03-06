import middleware from '../middleware';
import bodyParser from 'body-parser';
import url from 'url';
import fnv from 'fnv-plus'
import express, {
  Request,
  Response
} from 'express';
import { CreateShortLinkBody } from './interface'
import { query,getShortLink,insertLongLink } from '../db'
const app = express()
const port = 3000

// body 解析
app.use(bodyParser.urlencoded({ extended: false }));
// 校验输入合法性
app.use(middleware?.checkParameter)

// 输入长链，获取短链
app.post('/longLinkToShortLink', async (req: Request, res: Response, next) => {
  const body: CreateShortLinkBody = req.body;
  const { longLink } = body;
  const parsedUrl = url.parse(longLink);
  const baseUrl = `${parsedUrl?.protocol}//${parsedUrl?.host}`
  // 生成唯一id
  const id: String = fnv.fast1a32hex(longLink);
  // 生成短链
  const shortLink = `${baseUrl}/${id}`

  try {
    const [queryError, response] = await query(getShortLink(shortLink));
    if (queryError) {
      throw new Error(queryError)
    }
    if (response?.length) {
      res.json({
        short_link: response[0]?.short_link
      })
    }
    if (!response.length) {
      const [err, data] = await query(insertLongLink(shortLink, longLink))
      if (err) {
        throw new Error(err)
      }
      if (data) {
        res.json({
          shortLink
        })
      }
    }
  } catch (err) {
    console.error(err)
    next(err)
  }
})

// 输入短链，查询对应长链
app.get('/shortLinkToLongLink', async (req: Request, res: Response,next) => {
  const params: any = req.query;
  const shortLink = decodeURIComponent(params?.shortUrl)
  try {
    const [queryError, response] = await query(getShortLink(shortLink));
    if (queryError) {
      throw new Error(queryError)
    }
    if (response) {
      res.json({
        long_link: response[0]?.long_link
      })
   }
  } catch (err) {
    console.error(err)
    next(err)
  }
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})