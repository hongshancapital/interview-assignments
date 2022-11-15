import { Request, Response } from 'express';
import mongoose from 'mongoose';
import { cacheGet, cacheSet } from '../service/cache';
import { ReqInfo } from '../constant';

const Slink = mongoose.model('Slink');

const getLongUrl = async (req: Request, res: Response) => {
  const { id } = req.params;

  if (!id) {
    return res.status(400).json({ data: 'id为必传字段' });
  }

  let info = cacheGet(id); // 从缓存中取数据

  if (!info) {
    info = (await Slink.findOne({ id })) as ReqInfo;

    const cacheVal = {
      id: info.id,
      url: info.url,
    };

    cacheSet(id, cacheVal);
  }

  res.status(200).json({
    code: 0,
    message: 'success',
    data: {
      url: info.url,
    },
  });
};

export { getLongUrl };
