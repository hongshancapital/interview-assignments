import { Request, Response } from 'express';
import { Schema, model, Document, Query, Model } from 'mongoose';
import { cacheGet, cacheSet } from '../service/cache';
import { ReqInfo } from '../constant';

const getLongUrl = async (req: Request, res: Response) => {
  const id = req.query.id as string;

  if (!id) {
    return res.status(400).json({ data: 'id为必传字段' });
  }

  let info = cacheGet(id) as ReqInfo; // 从缓存中取数据

  if (!info) {
    const SlinkModel = model<ReqInfo>('Slink');

    SlinkModel.findOne({ id })
      .populate<{ id: string }>('id')
      .orFail()
      .then((doc) => {
        info = {
          id: doc.id,
          url: doc.url,
        };

        cacheSet(id, info);
        
        return res.status(200).json({
          code: 0,
          message: 'success',
          data: {
            url: info.url,
          },
        });
      })
      .catch(() => {
        return res.status(400).json({ data: '无效id' });
      });
  } else {
    return res.status(200).json({
      code: 0,
      message: 'success',
      data: {
        url: info.url,
      },
    });
  }
};

export { getLongUrl };
