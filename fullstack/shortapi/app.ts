import express from 'express'
import monk from 'monk'
import Joi from '@hapi/joi'
import shortId from 'shortid'
import validUrl from 'valid-url'
import config from 'config'

const app = express()
//support postman send 'application/json' style request
app.use(express.json())
//db connection  
const db = monk(`${config.get('mongoURI')}`)
const licslan = db.get('licslan')
//short linkk schema desgin
const schemaShortPath = Joi.object({
  shortCode: Joi.string().trim(),
  longUrl: Joi.string().trim().required(),
})




//POST API:Short Url maker
app.post('/shortLink', async (req, res) => {
  try {
    //校验格式字符串格式 这里尝试去校验 后面如果设计其他schema 可以使用到 此处可以省去
    await schemaShortPath.validateAsync(req.body)
    const { longUrl } = req.body

    //校验链接
    if (!validUrl.isUri(longUrl)) {
      res.status(401).json('Invalid long url')
    }

    //get data from mongodb by longUrl
    const url = await licslan.findOne({ longUrl: longUrl })

    //信息存在
    if (url) {
      res.json({
        shortUrl: `${config.get('baseUrl')}/${url.shortCode}`,
      })
    }
    //信息不存在
    else {
      var urlCode = shortId.generate()

      //短域名长度最大为 8 个字符（不含域名） 
      if (urlCode.length > 8) {
        urlCode = urlCode.substring(0, 8)
      }
      const object = {
        shortCode: urlCode,
        longUrl: longUrl,
      }
      //写入新的链接
      await licslan.insert(object)
      res.json({
        shortUrl: `${config.get('baseUrl')}/${urlCode}`,
      })
    }
  } catch (error) {
    res.status(500).json('Server error')
  }
})





//GET API:Get URL by shortUrl
app.get('/:shortCode', async (req, res) => {
  try {
    const { shortCode } = req.params
    const url = await licslan.findOne({ shortCode: shortCode })
    if (url) {
      // 重定向至原链接
      res.redirect(url.longUrl)
    } else {
      res.status(404).json('No url found')
    }
  } catch (error) {
    res.status(500).json('Server error')
  }
})



//3.Run server on 8000
const main = () => {
  //监听 8000 端口
  app.listen(`${config.get('port')}`, () => {
    console.log(`Running on ${config.get('baseUrl')}`)
  })
}
main()
