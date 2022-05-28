import * as express from 'express';
import { IRoute, Router } from 'express-serve-static-core';
import AsyncLock from 'async-lock';
import shortUrlService from '../services/shortUrl'
import config from '../config'
const lock = new AsyncLock();
let router: Router = express.Router();
shortUrlService.init();

/**
 * 短域名读取接口：接受短域名信息，返回长域名信息
 * @param urlId 短域名id {string}
 * @returns url 长域名 {string}
 */
router.get('/api/shortUrl/:urlId', async (req, res, next) => {
  if (req.params.urlId && /^[a-zA-Z0-9]{8}$/.test(req.params.urlId)) {
    const dbNo = req.params.urlId.substring(1, 3);
    const urlId = req.params.urlId.substring(3, 8);
    const url = await shortUrlService.queryShortUrl(dbNo, urlId);
    res.json({ url });
  } else {
    res.json({ error: 'invalid params: urlId' });
  }
});

/**
 * 短域名存储接口：接受长域名信息，返回短域名信息
 * @param url 长域名 {string}
 * @returns urlId 短域名id {string}
 */
router.post('/api/shortUrl', (req, res, next) => {
  lock.acquire('post_api_shortUrl', async function () {
    const url = req.body.url;
    let urlId = await shortUrlService.queryUrl(url);
    if (!urlId) {
      urlId = await shortUrlService.createShortUrl(url);
    }
    res.json({ urlId: config.partition_no + urlId });
  }).catch(function (err) {
    console.log('lock post_api_shortUrl error', err);
  });
});

module.exports = router;