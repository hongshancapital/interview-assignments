import  {
  Request,
  Response,
  NextFunction
} from 'express';

import Joi from 'joi'

// 校验参数是否合法
const checkParameter = function (req: Request, res: Response, next: NextFunction) {
  const body: CreateShortLinkBody = req.body;
  const schema = Joi.object({
    longLink: Joi.string().uri()
  })
  const { error } = schema.validate(body, { allowUnknown: false, abortEarly: true }); 
  if (error) {
    next(error);
    return null;
  }
  next()
}

export default  checkParameter 