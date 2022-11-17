import { Request, Response } from 'express';
const mongoose = require('mongoose');
const base62 = require('base-62.js');

const Slink = mongoose.model('Slink');

const createShortUrl = async (req: Request, res: Response) => {
  const { url } = req.body;

  if (!url) {
    return res.status(400).json({ data: 'url为必传字段' });
  }

  const link = new Slink();
  link.id = base62.encode(Math.floor(Date.now() / 24));
  link.url = url;

  const result = await link.save();

  res.status(200).json({
    code: 0,
    message: 'success',
    data: {
      shortId: result.id,
    },
  });
};

export { createShortUrl };
