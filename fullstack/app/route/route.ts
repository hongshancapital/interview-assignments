import { Request, Response} from 'express';
import { getShortUrl, getOriginUrl } from '../service/service';

export const getShortUrlApi = async (req: Request, res: Response) => {
  const originUrl = req.params.url || '';

  try {
    const shortUrl = await getShortUrl(originUrl);
    res.send({
      errCode: 0,
      shortUrl 
    });
  } catch(errCode) {
    res.send({
      errCode,
      errMsg: 'transform error'
    });
  }

  res.status(200).end();
}

export const getOriginUrlApi = async (req: Request, res: Response) => {
  const shortUrl = req.params.url || '';

  console.log(shortUrl);

  try {
    const originUrl = await getOriginUrl(shortUrl);

    res.send({
      errCode: 0,
      originUrl 
    });
  } catch(errCode) {

    let errMsg = 'error occured';

    res.send({
      errCode,
      errMsg
    });
  }

  res.status(200).end();
};