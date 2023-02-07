import { Request, Response } from 'express';
import { ShortUrlService } from '../service/shortUrl.service';
import { ShortUrlProvider } from '../provider/shortUrl.provider';
import { config } from '../common/config/index';
import { ErrorCode, HttpStatus } from '../common/interface/errorCode.interface';
import { validationResult } from 'express-validator';
const BASE_URL = config.baseUrl;
const shortUrlProvider = new ShortUrlProvider();
const shortUrlService = new ShortUrlService(shortUrlProvider);

export async function createShortUrl(req: Request, res: Response) {
  try {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      const reason = errors.array({
        onlyFirstError:true
      })[0];
      return res.status(HttpStatus.BAD_REQUEST).json({
        code: ErrorCode.ParamError,
        msg: `Param ${reason.param} is invalid value }`
      });
    }
    const { longUrl } = req.body;
    const shortUrl = await shortUrlService.saveShortUrl(longUrl);
    res.status(HttpStatus.OK).json({
      code: ErrorCode.Sucess,
      msg: "success",
      data: {
        shortUrl: BASE_URL + shortUrl,
      }
    });
  } catch (error) {
    res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
      code: ErrorCode.CreateShortUrlError,
      msg: '创建短链接失败！'
    })
  }
}

export async function getLongUrl(req: Request, res: Response) {
  try {
    const errors = validationResult(req);
    const reason = errors.array({
      onlyFirstError:true
    })[0];
    if (!errors.isEmpty()) {
      return res.status(HttpStatus.BAD_REQUEST).json({
        code: ErrorCode.ParamError,
        msg: `Param ${reason.param} is invalid value }`
      });
    }
    const shortUrl = req.query.shortUrl as string;
    const longUrl = await shortUrlService.getLongUrlByShortUrl(shortUrl);
    res.status(HttpStatus.OK).json({
      code: ErrorCode.Sucess,
      msg: "success",
      data: {
        longUrl,
      }
    });
  } catch (error) {
    res.status(HttpStatus.INTERNAL_SERVER_ERROR).json({
      code: ErrorCode.GetLongUrlError,
      msg: '获取长链接失败！'
    })
  }
}