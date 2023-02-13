/*
 * @Author: Json Chen
 * @Date: 2023-02-13 16:37:45
 * @LastEditors: Json Chen
 * @LastEditTime: 2023-02-13 18:19:22
 * @FilePath: /interview-assignments/fullstack/src/modules/short-url/service.ts
 */
import { generateShortId } from '../../utils/tools';
import { ShortUrl, ShortUrlModel } from './entity';
export const createShortUrlService = async (url: string):Promise<ShortUrl> =>{
  let existShortUrl = await ShortUrlModel.findOne({ url });

  if(existShortUrl){
    // 已存在,不生成了
    return existShortUrl;
  }

  let shortId = generateShortId();
  let shortUrl = await ShortUrlModel.findOne({ shortId });
  while (shortUrl) {
    // 确保唯一性
    shortId = generateShortId();
    shortUrl = await ShortUrlModel.findOne({ shortId });
  }
  shortUrl = new ShortUrlModel({ shortId, url });
  await shortUrl.save();
  return shortUrl;
} 

export const getShortUrlService = async (shortId: string):Promise<ShortUrl> =>  {
  const shortUrl = await ShortUrlModel.findOne({ shortId });
  return shortUrl;
} 
