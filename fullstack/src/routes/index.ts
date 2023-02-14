import * as express from 'express';
import * as validUrl from 'valid-url';
import { nanoid } from 'nanoid';
import { UrlModel } from '../models';
import { BASE_DOMAIN } from '../config';

export const router: express.Router = express.Router();

router.post('/longUrl', async (req, res) => {
  const { longUrl } = req.body;
  if (validUrl.isUri(longUrl)) {
    let urlModel = await UrlModel.findOne({ longUrl });
    // 如果长连接入库过，则直接返回短连接
    if (urlModel) {
      return res.json({
        shortUrl: `${BASE_DOMAIN}/${urlModel.shortUrlId}`,
      });
    }
    // 否则创建短连接并入库返回
    // 此处使用nanoid生成8位唯一id
    const shortUrlId = nanoid(8);
    urlModel = new UrlModel({
      longUrl,
      shortUrlId,
    });
    // 新生成的短连接入库
    await urlModel.save();
    return res.json({
      shortUrl: `${BASE_DOMAIN}/${shortUrlId}`,
    });
  }
  return res.status(401).json('请输入合法的域名~');
});

router.get('/shortUrl', async (req, res) => {
  const { shortUrl } = req.query;
  const urlModel = await UrlModel.findOne({ shortUrl });
  if (urlModel) {
    return res.json({
      longUrl: urlModel.longUrl,
    });
  }
  return res.status(404).json('该短连接无对应长连接');
});

export default router;
