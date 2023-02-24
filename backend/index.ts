import Express from 'express';
import BodyParser from 'body-parser';
import * as Redis from 'redis';
import MurmurHash3 from 'imurmurhash';
import { Sequelize } from 'sequelize';
import Models from './model';

const app = Express();
const PORT = 3000;

const sequelize = new Sequelize('mysql://root:jxa1234@localhost:3306/shorten');
const redisClient = Redis.createClient();
(async() => { await redisClient.connect() })();

Models.init(sequelize);

app.use(BodyParser.json({type: 'text/plain'}));

const Base62Encoder = (int: number) => {
  const CHARSET = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';

  let res = '';
  while(int > 0) {
    res = CHARSET[int % 62] + res;
    int = Math.floor(int / 62);
  }

  return res;
}

/*
存储长域名，返回对应短域名
输入: { domain: 'www.baidu.com' }
输出: { shorten: 'xxxxxxx' }
*/
app.post('/domain', async (req, res) => {
  try {
    const { domain } = req.body;

    // 对链接进行hash处理
    const hash = MurmurHash3(domain).result();

    // 生成短域名
    const shorten = Base62Encoder(hash);

    // 创建记录
    const exist = await Models.ShortDomain.findOne({where: {shorten}});
    if (!exist) {
      // 不存在 创建信息
      await Models.ShortDomain.create({shorten, domain});
    }

    res.status(200).json({code: 200, domain, shorten});
  }
  catch (e) {
    res.status(500).json({code: 500, err: ''});
  }
});

/*
通过短域名获取原始长域名
*/
app.get('/domain/:shorten', async (req, res) => {
  try {
    const { shorten } = req.params;
    let domain:any = await redisClient.get(shorten);
    
    if (!domain) {
      const record:any = await Models.ShortDomain.findOne({where: {shorten: req.params.shorten}});
      
      if (!record) {
        res.status(200).json({code: 500, err: '短域名不存在'});
        return;
      }

      await redisClient.setEx(shorten, 3600, record.domain);
    }

    res.status(200).send({domain});
  }
  catch (e) {
    res.status(500).json({code: 500, err: ''});
  }
});

app.listen(PORT, () => {
  console.log(`server start`);
});