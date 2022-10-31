import express, {Request, Response} from "express";
import {controller} from "./bootstrap";

const app = express()

app.post('/set', async (req: Request, res: Response) => {
  const url = req.query.url as string;
  //TODO 校验是否是url
  if (!url) {
    return res.status(400).json({message: 'url is required'});
  }
  const shortUrl = await controller.saveUrl(url);
  res.json({shortUrl});
})

app.get('/:shortUrl', async (req: Request, res: Response) => {
  const shortUrl = req.params.shortUrl;
  //todo:校验是否合法
  const url = await controller.getUrl(shortUrl);
  if (!url) {
    return res.status(404).send('not found');
  }
  res.redirect(url);
})

export {app}
