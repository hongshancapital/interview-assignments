import myDataSource from '../db/connection'
import { ShortUrlMapping } from '../entity/ShortUrlMapping'
import { Request, Response } from 'express'
import { BizResponse } from '../typings'
import generateUniqueId from '../utils/generateUniqueId'
import getHashCode from '../utils/getHashCode'

// 短链服务域名, 后续设置为环境变量，根据部署环境从config中取值
// 校验originalUrl是否合法
function checkUrl(url: string) {
  return /^(http|https):\/\/[\w\-_\u4E00-\u9FA5:/]+(\.[\w\-_\u4E00-\u9FA5]+)+([\u4E00-\u9FA5\w\-.,@?^=%&:/~+#]*[\u4E00-\u9FA5\w\-@?^=%&/~+#])?$/.test(url)
}

export async function generateShortUrl(req: Request, res: Response) {
  // 此接口需校验身份
  const shortUrlMappingRepository = myDataSource.getRepository(ShortUrlMapping)
  const response: BizResponse = {
    code: 200,
    message: '创建成功',
  }
  const postData = req.body
  // 校验参数是否合法
  if (postData?.originalUrl && checkUrl(postData.originalUrl)) {
    // 使用hash码与原链接查询是否库中已存在相应的短链
    // 若存在则返回，不存在则创建新纪录并返回
    const originalUrlHash = getHashCode(postData.originalUrl)
    // original_url_hash 建立索引加速查询, original_url 条件防止hash碰撞，保证结果唯一
    // where original_url_hash = $originalUrlHash AND original_url = $originalUrl
    let result = await shortUrlMappingRepository.findOneBy({
      originalUrlHash,
      originalUrl: postData.originalUrl,
    })
    let isNew = 0
    if (result === null) {
      isNew = 1
      let shortUrlCode = generateUniqueId()
      while (await shortUrlMappingRepository.findOneBy({
        shortUrlCode,
      }) !== null) {
        shortUrlCode = generateUniqueId()
      }
      result = await shortUrlMappingRepository.save(shortUrlMappingRepository.create({
        shortUrlCode,
        originalUrl: postData.originalUrl,
        originalUrlHash: getHashCode(postData.originalUrl),
      }))
    }
    response.data = {
      shortUrlCode: result.shortUrlCode,
      isNew,
    }
  } else {
    response.code = 10001
    response.message =  '创建失败,请输入正确的url地址'
  }
  res.json(response)
}

export async function responseShortUrl(req: Request, res: Response) {
  const { code } = req.query
  const response: BizResponse = {
    code: 200,
    message: '查询成功',
  }
  if (typeof code === 'string' && code.length === 8) {
    const result = await myDataSource.getRepository(ShortUrlMapping).findOneBy({
      shortUrlCode: code,
    })
    if (result !== null) {
      response.data = {
        originalUrl: result.originalUrl,
      }
    } else {
      response.code = 10002
      response.message = '未查询到绑定网址'
    }
  } else {
    response.code = 10003
    response.message = '网址不存在'
  }
  res.json(response)
}

