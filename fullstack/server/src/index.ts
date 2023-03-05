import middleware from '../middleware';
import bodyParser from 'body-parser';

import url from 'url';
import fnv from 'fnv-plus'
import express, {
  Request,
  Response
} from 'express';
import {CreateShortLinkBody} from './interface'
const app = express()
const port = 3000

// body 解析
app.use(bodyParser.urlencoded({ extended: false }));
// 校验输入合法性
app.use(middleware?.checkParameter)

// 输入长链，获取短链
app.post('/longLinkToShortLink', (req: Request, res: Response) => {
  const body: CreateShortLinkBody = req.body;
  const { longUrl } = body;
  // 生成唯一id
  const id: String = fnv.fast1a32hex(longUrl)
console.log(id, 'id')
  console.log(url.parse(longUrl))

  res.send('aaa1')
})

// 输入短链，查询对应长链
app.get('/shortLinkToLongLink', (req: Request, res: Response) => {
  const params = req.params;
  console.log(params, 'bodu')
  res.send('bbb')
})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})