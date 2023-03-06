import middleware from '../middleware';
import bodyParser from 'body-parser';
import url from 'url';
import fnv from 'fnv-plus'
import express, {
  Request,
  Response
} from 'express';
import { CreateShortLinkBody } from './interface'
import { query, inject } from '../db'
const app = express()
const port = 3000

// body 解析
app.use(bodyParser.urlencoded({ extended: false }));
// 校验输入合法性
app.use(middleware?.checkParameter)

// 输入长链，获取短链
app.post('/longLinkToShortLink', async (req: Request, res: Response, next) => {
  const body: CreateShortLinkBody = req.body;
  const { longUrl } = body;
  const parsedUrl = url.parse(longUrl);
  const baseUrl = `${parsedUrl?.protocol}//${parsedUrl?.host}`
  // 生成唯一id
  const id: String = fnv.fast1a32hex(longUrl);
  // 生成短链
  const shortLink = `${baseUrl}/${id}`

  try {
    const [queryError, response] = await query(`SELECT * FROM link_map_table WHERE short_link = '${shortLink}'`);
    if (queryError) {
      throw new Error(queryError)
    }
    if (response?.length) {
      res.json({
        short_link: response[0]?.short_link
      })
    }
    console.log(longUrl, 'longUrl')
    if (!response.length) {
      const [err, data] = await inject(`INSERT INTO link_map_table (short_link,long_link) VALUES ('${shortLink}', '${longUrl}' ) `)
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
    const [queryError, response] = await query(`SELECT * FROM link_map_table WHERE short_link = '${shortLink}'`);
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