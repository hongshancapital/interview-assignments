import express, {Application, Request, Response} from 'express';
import {getOriginUrl, getShortUrl} from './service';

const app: Application = express();

app.post('/shorturl/:originurl', async (req: Request, res: Response) => {
  const originUrl = req.params.originurl || '';

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
});

app.get('/shorturl/:shorturl', async (req: Request, res: Response) => {
  const shortUrl = req.params.shorturl || '';

  try {
    const originUrl = await getOriginUrl(shortUrl);

    res.send({
      errCode: 0,
      originUrl 
    });
  } catch(errCode) {
    res.send({
      errCode,
      errMsg: 'not found' 
    });
  }

  res.status(200).end();
});

app.listen(8080);