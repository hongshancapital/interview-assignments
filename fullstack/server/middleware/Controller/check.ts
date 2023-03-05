import  {
  Request,
  Response
} from 'express';
import { CreateShortLinkBody } from '../../src/interface';
import Joi from 'joi'

// 校验sql注入，待填坑
const checkParameter = function (req: Request, res: Response, next: () => void) {
  const body: CreateShortLinkBody = req.body;
  const schema = Joi.object({
    longUrl: Joi.string().uri()
  })

  const { error } = schema.validate(body, { allowUnknown: true, abortEarly: true }); 

  if (!error) { 
    next()
  } else {
    res.send('longUrl 校验失败')
  }
}

export default  checkParameter 