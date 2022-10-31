import {Request, Response} from "express";
import {controller} from "./bootstrap";
import express from "express";

const app = express()
const port = process.env["PORT"] || 3000

app.post('/set', async (req: Request, res: Response) => {
  const url = req.query.url as string;
  //TODO 校验是否是url
  if (!url) {
    return res.status(400).send('url is required');
  }
  const shortUrl = await controller.saveUrl(url);
  res.send(shortUrl);
})

app.get('/get/:shortUrl', async (req: Request, res: Response) => {
  const shortUrl = req.params.shortUrl;
  //todo:校验是否合法
  const url = await controller.getUrl(shortUrl);
  if (!url) {
    return res.status(404).send('not found');
  }
  res.redirect(url);
})

app.listen(port, () => {
  console.log(`app listening on port ${port}`)
})

