import  {
  Request,
  Response,
  NextFunction
} from 'express';

import Joi from 'joi'

enum Methods {
  Post = 'POST',
  Get = 'GET'
}

/**
   * @description: 中间件，查看输入的参数是否合法
   * @param {Request} req
   * @param {Response} res
   * @param {NextFunction} next
   * @return {*}
*/
const checkParameter: (req: Request, res: Response, next: NextFunction) => void = function (req: Request, res: Response, next: NextFunction) {
  const schemaPost  = Joi.object({
    longLink: Joi.string().required()
  })

  const schemaGet = Joi.object({
    shortLink: Joi.string().required()
  }) 
  if (req.method === Methods.Post) {
    const { error } = schemaPost.validate(req.body, { allowUnknown: false, abortEarly: true }); 
    if (error) {
      console.log('show error')
      next(error);
      return null;
    }
  } else {
    const { error } = schemaGet.validate(req.query, { allowUnknown: false, abortEarly: true }); 
    if (error) {
      console.log('show error')
      next(error);
      return null;
    }
  }
  
  next()
}

export default  checkParameter 