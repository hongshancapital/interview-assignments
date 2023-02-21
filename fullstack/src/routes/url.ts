import epxress from 'express';
import validUrl from 'valid-url';
import { UrlModel } from "../models/url";
const baseUrl = "http://localhost:5000"
const router = epxress.Router();

router.post('/shorten', async (req, res, next) => {
  const { longUrl } = req.body;
  if (validUrl.isUri(longUrl)) {
    try {
      let url = await UrlModel.findOne({ longUrl });
      if (url) {
        res.json({
          shortUrl: `${baseUrl}/${url.urlCode}`,
          code: url.urlCode
        });
      } else {
        // 唯一ID生成最好使用[雪花算法]，更符合高并发和大数据的需求
        // 这里使用简单的生成方式，10000k以内数据没有重复
        const urlCode = Math.random().toString(36).slice(-8)
        url = new UrlModel({
          longUrl,
          urlCode
        });
        await url.save();
        res.json({
          shortUrl: `${baseUrl}/${urlCode}`,
          code: urlCode
        });
      }
    } catch (error) {
      res.status(500).json('Server error');
    }
  } else {
    res.status(401).json('Invalid long url');
  }
});

export default router;