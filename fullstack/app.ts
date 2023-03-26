import 'reflect-metadata';
import Express, { Request, Response } from 'express';
import AppDataSource from './dataSource/mysql';
import { UrlMapping } from './entity/UrlMapping';
import { generateShortUrl } from './utils/UrlGenUtil';
import redisClient from './dataSource/redis';

const app = Express();
const port = 3000;

app.get('/generateShortUrl', async (req: Request, res: Response) => {
  const longUrl = req.query.longUrl as string;
  if (!longUrl) {
    res.status(400).send('Bad Request');
    return;
  }

  // 检查是否已经存在
  const result = await AppDataSource.manager.findOne(UrlMapping, { where: { longUrl } });
  if (result) {
    res.send(result.shortUrl);
    return;
  }

  // 生成短链接
  let shortUrl = '';
  let i = 0;
  // 5次生成尝试，避免生成相同的shortUrl(仅发生在相同的时间请求接口，且生成2位随机字符串也相同，概率为1 / 62^2，尝试5次可以将概率降低至 1 / 62^10)
  while (i++ < 5) {
    shortUrl = generateShortUrl();
    const cache = await redisClient.get(shortUrl);
    if (!cache) {
      break;
    }
  }
  if (!shortUrl) {
    res.status(500).send('请求太多了，请稍后重试');
    return;
  }
  redisClient.set(shortUrl, longUrl, { EX: 3600 * 24 });
  const urlMapping = new UrlMapping(shortUrl, longUrl);
  await AppDataSource.manager.save(urlMapping);
  res.send(shortUrl);
});

app.get('/tranferToLongUrl', async (req: Request, res: Response) => {
  const shortUrl = req.query.shortUrl as string;
  if (!shortUrl) {
    res.status(400).send('Bad Request');
    return;
  }
  // 先看Redis中是否有缓存
  const cache = await redisClient.get(shortUrl);
  if (cache) {
    res.send(cache);
    return;
  }
  // 去数据库中查找记录
  const result = await AppDataSource.manager.findOne(UrlMapping, { where: { shortUrl } });
  if (result) {
    // 在Redis中缓存一天，应对可能的同一短链接的大量请求
    redisClient.set(shortUrl, result.longUrl, { EX: 3600 * 24 });
    res.send(result.longUrl);
  } else {
    res.status(404).send('Not Found');
  }
});

app.get('/redis', async (req: Request, res: Response) => {
  redisClient.set('test', '1234');
  res.send('redis');
});

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`);
});
