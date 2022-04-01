import { Request, Response, NextFunction } from 'express';
import { redis } from '../libs/redis-connection';
import { BaseResponse, GetUrlReq, GetUrlRes } from '../types';
export async function hasCache(
  req: Request<GetUrlReq>,
  res: Response<BaseResponse<GetUrlRes>>,
  next: NextFunction
) {
  const longUrl = (await redis.get(req.params.shortUrl)) as string;
  if (longUrl) {
    console.log('redis cache hit');
    res.send({
      success: true,
      data: { longUrl },
    });
  } else {
    next();
  }
}
