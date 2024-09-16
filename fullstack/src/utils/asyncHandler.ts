import { NextFunction, Request, Response } from 'express'

type Handler = (req: Request, res: Response) => Promise<any>
type AsyncHandler<T extends Func> = (
  req: Request,
  res: Response,
  next: NextFunction,
) => Promise<ReturnType<T>>
export default function asyncHandler<T extends Handler>(fn: T): AsyncHandler<T> {
  return (req, res, next) => Promise.resolve(fn(req, res)).catch(next)
}
