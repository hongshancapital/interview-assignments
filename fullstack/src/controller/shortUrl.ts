import { NextFunction, Request, Response, Router } from 'express'
import shortUrlService from '../service/shortUrl'
import { ApiResult, ResponseStatus } from '../types/apiResult'
import { GenerateUrlParam } from '../types/shortUrl'

const router = Router()

router.post('/generate', (req: Request, res: Response, next: NextFunction) => {
  const params = req.body as GenerateUrlParam
  if (!params || !params.originUrl) {
    res.json(new ApiResult(ResponseStatus.paramError, null, '参数错误'))
    return
  }
  shortUrlService.generate(params)
  .then(shortUrl => {
    res.json(new ApiResult(ResponseStatus.success, shortUrl))
  })
  .catch(next)
})

router.post('/getOrigin', (req: Request, res: Response, next: NextFunction) => {
  const params = req.body as {
    shortUrl: string;
  }
  if (!params || !params.shortUrl) {
    res.json(new ApiResult(ResponseStatus.paramError, null, '参数错误'))
    return
  }
  shortUrlService.getOriginByShort(params.shortUrl)
  .then(data => {
    if (!data) {
      res.json(new ApiResult(ResponseStatus.fail, null, '找不到目标记录'))
      return
    }
    res.json(new ApiResult(ResponseStatus.success, data.originUrl))
  }).catch(next)
})

export default router