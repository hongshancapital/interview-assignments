import { Request, Response } from "express";

import ShortUrl from "../models/shortUrl";
function generateShortCode(maxLength: number) {
    const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    let result = '';

    // 生成一个随机长度，范围从1到给定的最大长度
    const length = Math.floor(Math.random() * maxLength) + 1;

    for (let i = 0; i < length; i++) {
      result += characters.charAt(Math.floor(Math.random() * characters.length));
    }
    return result;
}

  
  
  

  export const createShortUrl = async (req: Request, res: Response) => {
    const { longUrl } = req.body;
    const shortCode = generateShortCode(8);
  
    try {
      const shortUrl = new ShortUrl({
        longUrl: longUrl,
        shortCode: shortCode,
      });
  
      await shortUrl.save();
      res.status(201).json({ shortUrl: `http://your-domain.com/${shortCode}` });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: "An error occurred while creating short URL." });
    }
  };
  
  

  export const getLongUrl = async (req: Request, res: Response) => {
    const { code } = req.params;
  
    try {
      const shortUrl = await ShortUrl.findOne({ shortCode: code });
  
      if (!shortUrl) {
        res.status(404).json({ error: "Short URL not found." });
      } else {
        res.status(200).json({ longUrl: shortUrl.longUrl });
      }
    } catch (error) {
      res.status(500).json({ error: "An error occurred while fetching long URL." });
    }
  };
  
  
  
  
