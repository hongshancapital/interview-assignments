const express = require('express');
const validUrl = require('valid-url');
const ShortUniqueId = require('short-unique-id');
const config = require('../config');

const uid = new ShortUniqueId({ length: 8 });

// 延时
async function sleep(time) {
  return new Promise((resolve, reject) => {
    let timer = setTimeout(() => {
      resolve();
      clearTimeout(timer);
    }, time * 1000);
  });
}

function getExpires(now, n) {
  let v;
  switch (n) {
  // case 0: // 永久
  case 1: // 1分钟
    v = 60 * 1000;
    break;
  case 2: // 7天
    v = 7 * 24 * 60 * 60 * 1000;
    break;
  case 3: // 3个月
    v = 3 * 30 * 7 * 24 * 60 * 60 * 1000;
    break;
  case 4: // 半年
    v = 6 * 30 * 7 * 24 * 60 * 60 * 1000;
    break;
  }
  return new Date(now.getTime() + v);
}


module.exports = function(app) {
  const router = express.Router();

  // @route POST api/url/shorten
  // @desc 创建短链接
  router.post('/shorten', async(req, res) => {
    // await sleep(2);
    // 相关逻辑
    // 1.获取长链接
    const { longUrl, expires } = req.body;

    // 2.验证长连接
    console.log(longUrl, expires);
    if (!validUrl.isUri(longUrl)) {
      return res.status(401).json("无效Url");
    }

    try {
      const now = new Date();
      // 验证长连接是否已经存在数据库
      let [url] = await app.mysql.select('url', {
        columns: ['id', 'urlCode', 'longUrl', 'shortUrl', 'date', 'expireType', 'expires', 'refCount'],
        where: { longUrl, expireType: expires }
      });
      // console.log(url);
      if (url) {
        if (url.expireType !== 0) {
          if (url.expires < now) { // 已过期
            // 更新当前链接，复用之
            const data = {
              refCount: url.refCount + 1, // 更新复用次数，方便统计链接的活跃性
              date: now,
              expireType: expires,
              expires: getExpires(now, expires)
            };
            await app.mysql.update('url', data, { where: { id: url.id } });
            return res.json({
              ...url,
              ...data
            });
          } else { // 未过期
            // 直接使用
          }
        }
        await app.mysql.update('url', { refCount: url.refCount + 1 }, { where: { id: url.id } }); // 更新复用次数，方便统计链接的活跃性
        return res.json(url);
      }
      // 3.生成url code
      const urlCode = uid();
      // 4.生成短链接
      const shortUrl = config.baseUrl + urlCode;
      // 写入数据库
      url = {
        longUrl,
        shortUrl,
        urlCode,
        date: now,
        expireType: expires
      };
      if (expires !== 0) {
        url.expires = getExpires(now, expires);
      }
      const result = await app.mysql.insert('url', url);
      console.log(result);
      return res.json(url);
    } catch (ex) {
      console.error(ex);
      return res.status(500).json('Server error.');
    }
  });
  router.get('/get', async(req, res) => {
    const { code } = req.query;
    try {
      let [url] = await app.mysql.select('url', {
        columns: [/* 'id',  */'urlCode', 'longUrl', 'shortUrl', 'date', 'expireType', 'expires', 'refCount'],
        where: { urlCode: code }
      });
      // console.log(url);
      if (url) {
        return res.json(url);
      }
      return res.staus(404).json('Not found!');
    } catch (ex) {
      console.error(ex);
      return res.status(500).json('Server error!');
    }
  });
  return router;
};
