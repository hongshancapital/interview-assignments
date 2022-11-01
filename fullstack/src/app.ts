import express from "express";
import {service} from "./bootstrap";
import {isShortUrl, isUrl} from "./util";

const app = express()

app.post('/set', async (req, res) => {
  const url = req.query.url

  if (typeof url !== 'string' || !isUrl(url)) {
    return res.status(400).json({message: 'Illegal url'});
  }

  const shortUrl = await service.saveUrl(url);
  res.json({shortUrl});
})

app.get('/:shortUrl', async (req, res) => {
  const shortUrl = req.params.shortUrl;

  if (!shortUrl || !isShortUrl(shortUrl)) {
    return res.status(404).send('not found');
  }

  const url = await service.getUrl(shortUrl);
  if (!url) {
    return res.status(404).send('not found');
  }

  res.redirect(url);
})

export {app}
