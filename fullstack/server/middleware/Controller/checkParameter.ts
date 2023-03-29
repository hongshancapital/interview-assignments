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

const schemaMap = {
  [Methods.Post]: Joi.object({
    longLink: Joi.string().required()
  }),
  [Methods.Get]: Joi.object({
    shortLink: Joi.string().required()
  }) 
}

/**
   * @description: 中间件，查看输入的参数是否合法
   * @param {Request} req
   * @param {Response} res
   * @param {NextFunction} next
   * @return {*}
*/
const checkParameter: (req: Request, res: Response, next: NextFunction) => void = function (req: Request, res: Response, next: NextFunction) {
  const method: `${Methods}`  = req.method as any;
  
  const reqKey = {
    [Methods.Post]: req.body,
    [Methods.Get]: req.query
  }

  const { error } = schemaMap[method].validate(reqKey[method], { allowUnknown: false, abortEarly: true }); 
  if (error) {
    next(error);
    return null;
  }
  
  next()
}

export default  checkParameter 