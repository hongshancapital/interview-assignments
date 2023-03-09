import middleware from '../middleware';
import bodyParser from 'body-parser';
import parseShortLinkFromBody from '../utils/parseShortLinkFromBody'
import express, {
  Request,
  Response,
  NextFunction,
} from 'express';
import { getShortLink, insertLongLink, getValue, setValues } from '../db'

const app = express()
const port = 3000

// body 解析
app.use(bodyParser.urlencoded({ extended: false }));
// 校验输入合法性
app.use(middleware?.checkParameter)

// 输入长链，获取短链
app.post('/longLinkToShortLink', async (req: Request, res: Response, next: NextFunction) => {
  const { short_link, long_link } = parseShortLinkFromBody(req.body)
  try {
    const response: Result = await getValue(getShortLink(short_link), short_link, next);
    if (response) {
      res.json({
        short_link: decodeURIComponent(response.short_link)
      })
    }
    if (!response) {
      const value = await setValues(insertLongLink(short_link, long_link), short_link, long_link, next)
      if (value) {
        res.json({
          short_link: decodeURIComponent(short_link)
        })
      }
    }
  } catch (err) {
    console.error(err)
    next(err)
  }
})

// 输入短链，查询对应长链
app.get('/shortLinkToLongLink', async (req: Request, res: Response, next: NextFunction) => {
  const params: Params = req.query;
  const shortLink = typeof params.shortLink === 'undefined'? '': params.shortLink
  try {
    const response = await getValue(getShortLink(shortLink), shortLink, next);
    if (response) {
      res.json({
        long_link: decodeURIComponent(response?.long_link)
      })
    } else {
      res.send('记录不存在')
    }
  } catch (err) {
    console.error(err)
    next(err)
  }
})

app.use(function(err: any, req: Request, res: Response) {
  console.error(err.stack,'=======');
  res.status(500).send('Something broke!');
});

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})