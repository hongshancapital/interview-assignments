import express from 'express';

import { getContext } from '@/utils/context';
import { ShortUrlCreate } from './short-url.interface';
import * as shortUrlsService from './short-urls.service';

export const shortUrlsRouter = express.Router();

shortUrlsRouter.get('/:shortId', async (req, res) => {
  const shortId = req.params.shortId;
  const ctx = getContext(res);
  const shortUrl = await shortUrlsService.find(shortId, ctx);
  res.format({
    html: () => {
      if (shortUrl) {
        res.redirect(shortUrl.url);
      } else {
        res.status(404).send('<p>not found</p>');
      }
    },
    default: () => {
      if (shortUrl) {
        res.json(shortUrl);
      } else {
        res.status(404).send('not found');
      }
    },
  });
});

shortUrlsRouter.post('/', async (req, res) => {
  const ctx = getContext(res);
  const shortUrlCreate: ShortUrlCreate = req.body;
  const shortUrl = await shortUrlsService.create(shortUrlCreate, ctx);
  res.json(shortUrl);
});
