import { Request, Response } from 'express';
import url from "url";
import { ShortLinkInput } from '../models/ShortLink';
import TokenGenerator from '../tokenGenerators/TokenGenerator';
import SeqTokenGenerator from '../tokenGenerators/SeqTokenGenerator';
import ShortLinkService from '../services/ShortLinkService';
import configs from '../configs';
import ResUtil from '../utils/ResUtil';

export async function createShortLink(req: Request, res: Response) {
  try {
    const originalUrl:string = req.body.originalUrl as string;
    if (!originalUrl) {
      ResUtil.failed(res, "B10003");
      return;
    }
    if (originalUrl.length > 4000) {
      ResUtil.failed(res, "B10004");
      return;
    }
    
    if (!isValidUrl(originalUrl)) {
      ResUtil.failed(res, "B10005");
      return;
    }
  
    const shortUrl = await ShortLinkService.searchShortUrl(originalUrl);
    if (shortUrl) {
      ResUtil.success(res, configs.shortLinktHost + shortUrl);
      return;
    }
  
    const generator: TokenGenerator = await SeqTokenGenerator.getInstance();
  
    const shortLink: ShortLinkInput = {
      shortUrl: generator.generateToken(),
      originalUrl,
      userId: 1001, // 假设是从session中获取到的用户信息
      machineId: configs.generator.machineId,
    }
    await ShortLinkService.createShortLink(shortLink);
  
    ResUtil.success(res, configs.shortLinktHost + shortLink.shortUrl);
  } catch (err) {
    console.log(err);
    ResUtil.failed(res, "B10000", (err as Error).message);
  }
}

export async function getOriginalUrl(req: Request, res: Response) {
  try {
    let shortUrl = req.query.shortUrl as string;
    const parsedUrl = url.parse(shortUrl || "");
    if (!parsedUrl.path) {
      ResUtil.failed(res, "B10002");
    }

    const originalUrl = await ShortLinkService.searchOriginalUrl(parsedUrl.path?.substring(1) as string);
    if (originalUrl) {
      ResUtil.success(res, originalUrl);
    } else {
      ResUtil.failed(res, "B10001");
    }
  } catch (err) {
    ResUtil.failed(res, "B10000", (err as Error).message);
  }
}

const isValidUrl = function(url: string) : boolean {
  const reg = /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
  return reg.test(url);
}