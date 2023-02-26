import * as express from 'express';
import { Express } from 'express';
import { hash, toBase62 } from './utils';

const router = express.Router();

// 支持的协议
const protocols = ['http:', 'https:'];
// ttl
const ttl = 60 * 60 * 24 * 30; // 30 days

// 使用redis作为数据库，采用hash存储，以便于后续的扩展。
// 拆分url，存储协议和主体，略微节省空间。
type sUrlInfo = {
  u: string; // url主体
  p: string; // 协议
};

export function genSUrlToken(url: string) {
  return toBase62(hash(url));
}

router.get('/:sUrl', async (req, res, next) => {
  // 短网址请求，重定向到原网址
  const sUrl = req.params.sUrl;
  if (sUrl) {
    const result = (await req.redis.hgetall(sUrl)) as sUrlInfo;
    if (result?.u) {
      const url = protocols[parseInt(result.p)] + '//' + result.u;
      res.redirect(url);
      // 为它续命
      return req.redis.expire(sUrl, ttl);
      // TODO 可以增加统计信息，以便追踪短网址的使用情况，得到热门数据。
      // req.redis.zadd('views', 1, sUrl);
    }
  }
  res.redirect(process.env.BASE_URL);
});

router.post('/', async (req, res, next) => {
  // 申请短网址
  const url = req.body?.url?.trim();
  if (url) {
    // 检查url是否合法
    try {
      const target = new URL(url);
      const protocol = protocols.findIndex((protocol) => protocol === target.protocol);
      if (protocol >= 0) {
        const u = url.substring(protocols[protocol].length + 2);

        // 生成短网址，利用murmurhash算法减少碰撞几率
        let token = genSUrlToken(url);

        // 检查是否碰撞
        const original = token;
        let warning = 10;
        while (warning > 0) {
          const existing = await req.redis.hget(token, 'u');
          if (existing) {
            warning--;
            // 碰撞了，检查是否是同一个url
            if (existing === u) {
              break;
            }

            // 碰撞了，考虑扩展token长度
            const count = await req.redis.incr(`c:${token}`);
            const prefix = toBase62(count);
            token = prefix + token;

            if (token.length > 8) {
              // token长度已经达到8位，使用murmurhash算法生成新的token
              token = genSUrlToken(original + '|' + warning + '|' + url);
            }
          } else {
            break;
          }
        }

        if (warning <= 0) {
          // 重试10次仍然碰撞，放弃并警告
          console.warn('collision warning:', token, url);
          res.status(500).send('Internal Server Error');
          return;
        } else if (warning === 10) {
          // 没有碰撞，保存
          await req.redis.hmset(token, { p: protocol, u });
        }

        // 续命
        await req.redis.expire(token, ttl);

        return res.status(200).send({
          sUrl: token,
          expireIn: ttl,
        });
      }
    } catch (err) {
      console.warn('url is not valid:', url);
    }
  }

  res.status(400).send('Bad Request');
});

export default function (app: Express) {
  app.use('/', router);
}
