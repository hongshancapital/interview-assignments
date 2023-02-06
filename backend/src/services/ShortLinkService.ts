import { ShortLinkInput, ShortLinkOuput } from "../models/ShortLink";
import ShortLinkDao from "../daos/ShortLinkDao";
import lruCache from "../cache/lruCache";
import RedisFactory from "../cache/RedisFactory";
import configs from "../configs";

const { REDIS_TTL, LRU_MISS_TTL } = configs;
export async function createShortLink(payload: ShortLinkInput): Promise<ShortLinkOuput> {
  const shortLink = await ShortLinkDao.createShortLink(payload);
  await RedisFactory.getClient().set(`machineId:${shortLink.machineId}:latestShortUrl`, shortLink.shortUrl);
  lruCache.set(shortLink.shortUrl, shortLink.originalUrl);
  await RedisFactory.getClient().setex(`shortUrl:${shortLink.shortUrl}`, REDIS_TTL, shortLink.originalUrl);
  return shortLink;
}

export async function searchShortUrl(originalUrl: string): Promise<string | undefined> {
  const shortLink = await ShortLinkDao.findByOriginalUrl(originalUrl);
  return shortLink?.shortUrl;
}

async function searchOriginalUrl(shortUrl: string): Promise<string | undefined> {
  return searchOriginalUrlByLRU(shortUrl);
}

export default {
  createShortLink, searchShortUrl, searchOriginalUrl
}

async function searchOriginalUrlByLRU(shortUrl: string): Promise<string | undefined> {
  let originalUrl:string | undefined = lruCache.get(shortUrl);
  if (originalUrl) return originalUrl;

  originalUrl = await searchOriginalUrlByRedis(shortUrl);

  // 应对恶意频繁访问，缓存空数据 15 分钟
  if (originalUrl == undefined) {
    originalUrl = "";
    lruCache.set(shortUrl, originalUrl, {
      ttl: LRU_MISS_TTL,
    });
  } else {
    lruCache.set(shortUrl, originalUrl);
  }
  return originalUrl;
}

async function searchOriginalUrlByRedis(shortUrl: string): Promise<string | undefined> {
  let originalUrl:string | undefined = await RedisFactory.getClient().get(`shortUrl:${shortUrl}`) as string | undefined;
  if (originalUrl) return originalUrl;

  originalUrl = await searchOriginalUrlByDB(shortUrl);
  if (originalUrl) {
    await RedisFactory.getClient().setex(`shortUrl:${shortUrl}`, REDIS_TTL, originalUrl);
  }
  return originalUrl;
}

async function searchOriginalUrlByDB(shortUrl: string): Promise<string | undefined> {
  const shortLink = await ShortLinkDao.findByShortUrl(shortUrl);
  return shortLink?.originalUrl;
}
