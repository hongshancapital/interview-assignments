/** @format */

import express from 'express';
import { splitUrl } from './helpers';
import { connectDB } from './db';
import validUrl from 'valid-url';
import Url from './model/url';
import { nanoid } from 'nanoid';

const app = express();

connectDB();

app.get('/getShortDomainName', async (req, res) => {
  const longUrl = req.query.longUrl as string;
  try {
    if (validUrl.isUri(longUrl)) {
      let url = await Url.findOne({ longUrl });
      if (url) {
        res.json({
          shortUrl: `${splitUrl(longUrl)[0]}${url.urlCode}`,
        });
      } else {
        const urlCode = nanoid(8);
        url = new Url({
          longUrl,
          urlCode,
        });
        await url.save();
        res.json({
          shortUrl: `${splitUrl(longUrl)[0]}${url.urlCode}`,
        });
      }
    } else {
      res.status(401).json('Invalid long url');
    }
  } catch (err) {
    res.status(500).json('Server Error');
  }
});

app.get('/getOriginUrl', async (req, res) => {
  try {
    const urlCode = req.query.urlCode as string;
    const url = await Url.findOne({ urlCode });
    if (url) {
      res.json({
        originUrl: url.longUrl,
      });
    } else {
      res.status(404).json('No url found');
    }
  } catch (err) {
    res.status(500).json('Server Error');
  }
});

export default app;
