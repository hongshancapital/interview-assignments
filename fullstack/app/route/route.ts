import { Request, Response } from 'express';
import { getShortUrl } from '../service/getShortUrl';
import { getOriginUrl } from '../service/getOriginUrl';

import { SUCCESS, errMsgMap } from '../common/errCode';
import { getErrCode } from '../common/errHandler';

interface ResponseData {
  errCode: string,
  errMsg?: string,
  data?: {}
}

function getParam(obj: any, name: string) : string {
  if (obj[name]) {
    return obj[name];
  }

  return '';
}

// TODO 域名拼接到短链

export const getShortUrlApi = async (req: Request, res: Response) => {
  const originUrl = getParam(req.body, 'url');

  try {
    const shortUrl = await getShortUrl(originUrl);
    const resData: ResponseData = {
      errCode: SUCCESS,
      data: { url: shortUrl }
    };

    res.send(resData);
  } catch(err) {
    let errCode = getErrCode(err);
    const resData: ResponseData = {
      errCode,
      errMsg: errMsgMap[errCode]
    };

    res.send(resData);
  }

  res.status(200).end();
}

export const getOriginUrlApi = async (req: Request, res: Response) => {
  const shortUrl = getParam(req.query, 'url');

  try {
    const originUrl = await getOriginUrl(shortUrl);
    const resData: ResponseData = {
      errCode: SUCCESS,
      data: { url: originUrl }
    };

    res.send(resData);
  } catch(err) {
    let errCode = getErrCode(err);
    const resData: ResponseData = {
      errCode,
      errMsg: errMsgMap[errCode]
    };

    res.send(resData);
  }

  res.status(200).end();
};