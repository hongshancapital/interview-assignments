import express, {Request, Response} from "express";
import {controller} from "./bootstrap";
import {isShortUrl, isUrl} from "./util";

const app = express()

app.post('/set', async (req: Request, res: Response) => {
  const url = req.query.url as string;

  if (!url) {
    return res.status(400).json({message: 'url is required'});
  }

  if (!isUrl(url)) {
    return res.status(400).json({message: 'Illegal url'});
  }

  const shortUrl = await controller.saveUrl(url);
  res.json({shortUrl});
})

app.get('/:shortUrl', async (req: Request, res: Response) => {
  const shortUrl = req.params.shortUrl;

  if (!shortUrl || !isShortUrl(shortUrl)) {
    return res.status(404).send('not found');
  }

  const url = await controller.getUrl(shortUrl);
  if (!url) {
    return res.status(404).send('not found');
  }

  res.redirect(url);
})

export {app}
