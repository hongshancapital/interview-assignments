import { Request, Response } from 'express';
import mongoose from 'mongoose';
import { cacheGet, cacheSet } from '../service/cache';
import { ReqInfo } from '../constant';

const Slink = mongoose.model('Slink');

const getLongUrl = async (req: Request, res: Response) => {
  const { id } = req.params;

  console.log(id);
  if (!id) {
    console.log('111');
    return res.status(400).json({ data: 'id为必传字段' });
  }

  let info = cacheGet(id) as ReqInfo; // 从缓存中取数据

  if (!info) {
    info = await Slink.findOne({ id }, ()=>{}).catch((err)=>{
      console.log(err);
      console.log('33333');
      return res.status(400).json({ data: err });
    })

    
    // .catch((err) => {
    //   console.log('333333')
    //   console.log(err);
    //   return res.status(400).json({ data: err });
    // });

    // const cacheVal = {
    //   id: info.id,
    //   url: info.url,
    // };

    // cacheSet(id, cacheVal);
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
