import  {
  Request,
  Response
} from 'express';
import { CreateShortLinkBody } from '../../src/interface';
import Joi from 'joi'

// 校验参数是否合法
const checkParameter = function (req: Request, res: Response, next: () => void) {
  const body: CreateShortLinkBody = req.body;
  const schema = Joi.object({
    longLink: Joi.string().uri()
  })
  try {
    const { error } = schema.validate(body, { allowUnknown: false, abortEarly: true }); 
    if (error) {
      throw new Error('longLink校验失败')
    }
    next()
  } catch (err) {
    console.log(err, 'err')
    res.send(err)
  }
}

export default  checkParameter 