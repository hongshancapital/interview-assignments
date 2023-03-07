import { Request, Response } from 'express'
import redisClient from '../helper/redis'
import hashHelper from '../helper/hash'

const shorturlController = {
  async getUrl (req: Request, res: Response) {
    const { hash } = req.params;

    const client = await redisClient();
    const result = await client.get(hash) as string;
    res.redirect(result);

  },
  async setUrl (req: Request, res: Response) {
    const url = req.body.url;
    const hash = hashHelper.getHashes(url);
    const client = await redisClient();
    await client.set(hash, url);
    res.json({ hash });
  }
}

export default shorturlController


