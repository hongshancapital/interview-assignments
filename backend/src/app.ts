import express from 'express';
import mongoose from "mongoose";
import bodyParser from 'body-parser';
import { ShortUrlController } from './controllers/shortUrl';

const app = express();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

const shortUrlController = new ShortUrlController();

mongoose.connect('mongodb://localhost:27017/shortUrl', {}, (err) => {
  if (err) {
    console.error(err);
    process.exit(1);
  }
});

// 短域名存储接口
app.post('/shortUrls', async (req, res) => {
  const { longUrl } = req.body;

  if (!longUrl) {
    return res.status(400).send('Missed longUrl parameter');
  }

  const shortUrl = await shortUrlController.createShortUrl(longUrl);
  if (!shortUrl) {
    return res.status(500).send('Failed to create shortUrl');
  }

  return res.send({ shortUrl });
});

// 短域名读取接口
app.get('/shortUrls/:shortUrlId', async (req, res) => {
  const { shortUrlId } = req.params;

  if (!shortUrlId) {
    return res.status(400).send('Missed shortUrlId parameter');
  }

  const longUrl = await shortUrlController.getLongUrl(shortUrlId);
  if (!longUrl) {
    return res.status(404).send('Short URL not found');
  }

  return res.send({ longUrl });
});

const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`Server started on port ${port}`);
});

export { app };