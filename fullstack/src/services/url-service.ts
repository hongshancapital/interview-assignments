import urlRepo from "../../src/repos/url-repo";
import {IUrl} from "../../src/models/Url";
import {Md5} from "ts-md5";
import {generateShortUrl} from "../../src/declarations/functions";

// **** Variables **** //

export const urlNotFoundErr = "OriginalUrl is not found!";

// **** Functions **** //

/**
 * Get short url.
 */
async function getShortUrlInfo(originalUrl: string): Promise<IUrl> {
  // 判断长链接Md5值是否存在
  const urlHash = Md5.hashStr(originalUrl).toUpperCase();
  const findOne = await urlRepo.getOne(urlHash);
  if (findOne) {
    return findOne;
  }
  const shortUrl = generateShortUrl();
  // 由于使用的mock数据库，此处使用了id来填充，不同数据库此处创建逻辑会修改
  const newUrl = await urlRepo.add({id: -1, shortUrl, urlHash, originalUrl});
  return newUrl;
}

/**
 * Get original url.
 */
async function getOriginalUrlInfo(shortUrl: string): Promise<IUrl | null> {
  // 此处单独写了使用shortUrl参数获取记录的方法，使用orm以及不同数据库是，逻辑都需要微调。
  const originalUrl = await urlRepo.getOneByShortUrl(shortUrl);
  return originalUrl;
}

// **** Export default **** //

export default {
  getShortUrlInfo,
  getOriginalUrlInfo,
} as const;
