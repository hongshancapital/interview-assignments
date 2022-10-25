import { Request, Response } from 'express';
import { getShortUrl } from '../service/getShortUrl';
import { getOriginUrl } from '../service/getOriginUrl';

import { SUCCESS, errMsgMap } from '../common/errCode';
import { getErrCode } from '../common/errHandler';
import { addShortDomain, removeShortDomain } from '../common/url';

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

export const getShortUrlApi = async (req: Request, res: Response) => {
  const originUrl: string = getParam(req.body, 'url');

  try {
    let shortUrl: string = await getShortUrl(originUrl);
    shortUrl = addShortDomain(shortUrl);

    const resData: ResponseData = {
      errCode: SUCCESS,
      data: { url: shortUrl }
    };

    res.send(resData);
  } catch(err) {
    let errCode: string = getErrCode(err);
    const resData: ResponseData = {
      errCode,
      errMsg: errMsgMap[errCode]
    };

    res.send(resData);
  }

  res.status(200).end();
}

export const getOriginUrlApi = async (req: Request, res: Response) => {
  let shortUrl: string = getParam(req.query, 'url');
  shortUrl = removeShortDomain(shortUrl);

  try {
    const originUrl: string = await getOriginUrl(shortUrl);
    const resData: ResponseData = {
      errCode: SUCCESS,
      data: { url: originUrl }
    };

    res.send(resData);
  } catch(err) {
    let errCode: string = getErrCode(err);
    const resData: ResponseData = {
      errCode,
      errMsg: errMsgMap[errCode]
    };

    res.send(resData);
  }

  res.status(200).end();
};