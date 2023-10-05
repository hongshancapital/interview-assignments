import { Request, Response, NextFunction } from 'express';
import shortUrlService from '../service/shortUrl';

/**
 * 接受长域名信息，返回短域名信息
 * @param req
 * @param res
 * @param next
 */
async function generate(
  req: Request,
  res: Response,
  next: NextFunction
): Promise<any> {
  const originUrl: string = req.body.originUrl; // 长连接
  const appId: string = req.body.appId; // 应用方id
  if (!originUrl || originUrl.length > 200 || !appId) {
    return res.status(400).json({ message: '参数错误' });
  }
  try {
    const shortUrl: string = await shortUrlService.generate(appId, originUrl);
    // return res.redirect(302, "shortUrl"); // 临时重定向，因为可以根据这个临时重定向统计短链的点击次数
    res.status(200).send({ shortUrl });
  } catch (e: any) {
    // 可以后续扩展成 全局捕获，封装成BadRequest等之类的对象，根据对象返回
    res.status(400).send({ message: e.message });
  }
}

/**
 * 接受短域名信息，返回长域名信息
 * @param req
 * @param res
 * @param next
 */
async function getOriginUrl(
  req: Request,
  res: Response,
  next: NextFunction
): Promise<Response> {
  const shortCode = req.query.shortCode;
  if (!shortCode || typeof shortCode != 'string' || shortCode.length > 8) {
    return res.status(400).json({ message: '参数错误' });
  }
  try {
    const originUrl: string = await shortUrlService.getOriginUrl(shortCode);
    return res.status(200).send({ originUrl });
  } catch (e: any) {
    // 可以后续扩展成 全局捕获，封装成BadRequest等之类的对象，根据对象返回
    return res.status(400).send({ message: e.message });
  }
}

export default {
  generate,
  getOriginUrl
};
