import  {
  Request,
  Response,
  NextFunction
} from 'express';

import Joi from 'joi'

/**
   * @description: 中间件，查看输入的参数是否合法
   * @param {Request} req
   * @param {Response} res
   * @param {NextFunction} next
   * @return {*}
*/
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