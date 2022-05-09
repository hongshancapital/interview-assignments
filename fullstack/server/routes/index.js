const express = require('express');
const dayjs = require('dayjs');

module.exports = function(app) {
  const router = express.Router();

  // @route GET /:code
  // @desc 重定向到长链接
  router.get('/:code', async(req, res) => {
    // 相关逻辑
    const [url] = await app.mysql.select('url', { where: { urlCode: req.params.code } });
    // 检测url是否存在
    if (url) {
      // res.redirect(url.longUrl);
      res.type('html');
      res.render('index.ejs', {
        dayjs,
        url: url.longUrl,
        date: url.date,
        expires: url.expires
      });
    } else {
      res.status(404).json("Server error");
    }
  });
  return router;
};
