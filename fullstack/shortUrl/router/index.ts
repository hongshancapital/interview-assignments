import express, { Router, Request, Response } from 'express'
const router = Router();
const validUrl = require('valid-url');
const shortId = require('shortid');
const Url = require('../models/url');
const baseShortUrl = "http://localhost:7001/"


type URL = {
  fullUrl: string,
  shotUrlCode: string,
  _id: string,
  __v: string,
}



router.post("/queryFullUrl", async (req: Request, res: Response) => {

  const { shotUrl } = req.body;
  //判断url是否有效
  if (validUrl.isUri(shotUrl)) {
//获取短链接的code
    let shotUrlCode = shotUrl.substring(shotUrl.length - 7)
//查询是否存在 
    let urlItem = await Url.findOne({ shotUrlCode });
//返回长链接
    if (urlItem) {
      res.send({
        status: 200,
        msg: "获取原链接成功",
        fullUrl: urlItem.fullUrl
      })
    } else {
      res.send({
        status: 400,
        msg: "链接不存在",
      })
    }

  }else{
    res.send({
      status: 400,
      msg: "链接无效"
    })
  }

})



router.post("/exchangeShortUrl", async (req: Request, res: Response) => {

  const { fullUrl } = req.body;
  //判断url是否有效
  if (validUrl.isUri(fullUrl)) {
    // 查询长链接是否已存在
    let urlItem = await Url.findOne({ fullUrl });

    if (urlItem) {
      //如果存在 返回短链接
      res.send({
        status: 200,
        msg: "获取短链接成功",
        shortUrl: baseShortUrl + urlItem.shotUrlCode
      })
    } else {
      //不存在
      //生成短链接 
      let shotUrlCode = shortId.generate().substr(0, 7);
      let urlItem = {
        fullUrl,
        shotUrlCode
      }
      //并存储
      Url.create(urlItem).then((doc:URL) => {
        //返回短链接
        console.log(doc);
        res.send({
          status: 200,
          msg: "获取短链接成功",
          shortUrl: baseShortUrl + doc.shotUrlCode
        })
      }).catch(()=>{
        res.send({
          status: 500,
          msg: "服务异常获取失败"
        })
      })

    }


  } else {
    res.send({
      status: 400,
      msg: "链接无效"
    })
  }


})

module.exports = router

