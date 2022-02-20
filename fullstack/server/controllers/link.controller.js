const os = require('os');
const Hashids = require("hashids");
const db = require('../models');
const { getBytes } = require('../utils/string');

const LinkModel = db.link;
const shortLinkReg = /^[a-zA-Z0-9]{1,8}$/;
const orginLinkReg = /(http|ftp|https):\/\/[\w\-_]+(.[\w\-_]+)+([\w\-.,@?^=%&:/~+#]*[\w\-@?^=%&/~+#])?/;
/**
 * url limit to explorer
 * 1、IE 2048
 * 2、360 2118
 * 3、Firefox 65536
 * 4、Safari 80000
 * 5、Opera 190000
 * 6、Chrome 8182
 */

// 自增序列算法可能更好一些，如果只有一个 master 专用写更好
const linkGenerator = (id) => {
  const hashid = new Hashids(os.hostname());
  let hash = hashid.encode(id);
  if (hash.length > 8) {
    hash = hash.substr(0, 8);
  }
  return hash;
}

exports.create = async (req, res) => {
  let { link } = req.body;

  if (Object.prototype.toString.call(link) !== '[object String]') {
    res.json({
      message: "Params(link) should be a string!",
      code: 400
    });
    return;
  }

  link = link.trim();
  if (!link) {
    res.json({
      message: "Params(link) can not be empty!",
      code: 400
    });
    return;
  }

  if (!orginLinkReg.test(link)) {
    res.json({
      message: "Params(link) not valid! Please check your input.",
      code: 400
    });
    return;
  }

  const bytes = getBytes(link);
  if (bytes > 2118) {
    res.json({
      message: "Params link is too long! The limit bytes is 2118.",
      code: 414
    });
    return;
  }

  const total = await LinkModel.count();
  const shortLink = linkGenerator(total + 1);

  const insertData = {
    origin: link,
    short: shortLink
  };

  try {
    await LinkModel.create(insertData);
    res.json({
      message: 'success',
      code: 0,
      data: {
        ...insertData,
        domain: 'http://localhost'
      }
    });
  } catch (err) {
    // 如果是因为 shortlink 重复导致的创建失败，则证明算法需要改进
    // todu: reportError
    res.send({
      message:
        err.message || "Service not avaliable. Please contact the administrator!",
      code: 500
    });
  }
};

exports.find = async (req, res) => {
  let { key } = req.query;
  key = key.trim();

  if (!key) {
    res.json({
      message: "Params(key) can not be empty!",
      code: 400
    });
    return;
  }

  if (key.includes('//')) {
    const url = new URL(key);
    key = url.pathname.split('/')[1];
  }
  
  if (!shortLinkReg.test(key)) {
    res.send({
      message: "Short link path not valid. Please check your input!",
      code: 400
    });
    return;
  }

  const dataSet = await LinkModel.findOne({ where: { short: key } });
  if (dataSet) {
    res.json({
      message: 'success',
      code: 0,
      data: dataSet.origin
    });
  } else {
    res.json({
      message: 'No origin url found. Please check the input.',
      code: 404
    });
  }
};
