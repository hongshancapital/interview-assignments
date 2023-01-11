import { Router } from 'express';
import { nanoid } from 'nanoid';
import { isUri } from 'valid-url';
import { baseUrl } from '../config/default';
import { IUrl, UrlModel } from '../models/url';
import { responseCode } from '../utils/responseCode';

const router = Router();

router.get('/restore/:code', async (req, res) => {
  const { code } = req.params;
  const data = await UrlModel.findOne({ shortenCode: code }).catch(err => {
    res.json(responseCode('internal_error', {}, err.message));
  });
  if (data) {
    res.json(responseCode('success', {
      code,
      url: data.initUrl
    }));
  } else {
    res.json(responseCode('arg_error', {}, 'Invalid Short Url'));
  }
});

router.post('/shorten', async (req, res) => {
  const { url } = req.body || {};
  if (isUri(url)) {
    let shortenCode = '';
    try {
      const data: IUrl = await UrlModel.findOne({ initUrl: url });
      if (data) {
        shortenCode = data.shortenCode;
      } else {
        shortenCode = nanoid(8);
        await new UrlModel({ initUrl: url, shortenCode }).save();
      }
      res.json(responseCode('success', {
        shortenCode,
        shortUrl: `${baseUrl}/${shortenCode}`
      }));
    } catch (err) {
      res.json(responseCode('internal_error', {}, err.message));
    }
  } else {
    res.json(responseCode('arg_error', {}, `Invalid Url: ${url}`));
  }
});

export default router;