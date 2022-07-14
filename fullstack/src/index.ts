import express from 'express'
import ShortUrl from './modules/shorturl'

const app = express()

/*
 * 短网址生成接口
 * @param url_long
 */
app.get('/shorturl/encode', async (req, res) => {
  const url_long: string = req.query ? (req.query as {})['url_long'] : ''
  const result = await ShortUrl.encode(url_long)
  res.send(result)
})

/*
 * 短网址生成接口
 * @param url_short
 */
app.get('/shorturl/decode', async (req, res) => {
  const url_short: string = req.query ? (req.query as {})['url_short'] : ''
  const result = await ShortUrl.decode(url_short)
  res.send(result)
})

export const server = app.listen(3000)
