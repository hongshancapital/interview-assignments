import { IUrl } from "../../src/models/Url";
import { Md5 } from "ts-md5";
import { generateShortUrl } from "../../src/declarations/functions";
import { AppDataSource } from '../data-source';
import { Url } from "../entity/Url";

// **** Variables **** //

export const urlNotFoundErr = "OriginalUrl is not found!";
const urlRepository = AppDataSource.getRepository(Url);

// **** Functions **** //

/**
 * Get short url.
 */
async function getShortUrlInfo(originalUrl: string): Promise<IUrl> {
  // 判断长链接Md5值是否存在
  const urlHash = Md5.hashStr(originalUrl).toUpperCase();
  const findOne = await urlRepository.findOne({ where: { urlHash } });
  if (findOne) {
    return findOne;
  }
  const shortUrl = generateShortUrl();
  const url = Object.assign(new Url(), {
    originalUrl,
    shortUrl,
    urlHash,
  });
  const newUrl = await urlRepository.save(url);
  return newUrl;
}

/**
 * Get original url.
 */
async function getOriginalUrlInfo(shortUrl: string): Promise<IUrl | null> {
  const originalUrl = await urlRepository.findOne({ where: { shortUrl } });
  return originalUrl;
}

// **** Export default **** //

export default {
  getShortUrlInfo,
  getOriginalUrlInfo,
} as const;
