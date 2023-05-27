import { cacheEx, getFromCache, queryDataBase } from "./common";
import crypto from "crypto";

export interface ShortUrlRecord {
  url: string;
  shortKey: string;
  fullKey: string;
}

export function generateShortUrl(
  url: string,
  shortKeyLength: number = 8
): ShortUrlRecord {
  const fullKey = crypto
    .createHash("sha1")
    .update(url)
    .digest("hex")
    .toLowerCase();
  const shortKey = fullKey.slice(0, shortKeyLength);
  return {
    url,
    fullKey,
    shortKey,
  };
}

/**
 * 通过全 Hash 查询记录
 * @param key
 * @returns
 */
async function saveShortUrlRecord(record: ShortUrlRecord) {
  await queryDataBase<ShortUrlRecord[]>(
    "insert into short_urls(url, short_url_key, full_url_key) values(?, ?, ?)",
    [record.url, record.shortKey, record.fullKey]
  );
}

/**
 * 通过全 Hash 查询记录
 * @param key
 * @returns
 */
async function getShortUrlRecordByFullKey(key: string) {
  const results = await queryDataBase<ShortUrlRecord[]>(
    "select url, short_url_key as shortKey, full_url_key as fullKey from short_urls where full_url_key = ?",
    key
  );
  if (results.length > 0) {
    return results[0];
  }
  return null;
}

/**
 * 通过短链接表示查询记录
 * @param key
 * @returns
 */
async function getShortUrlRecordByShortKey(key: string) {
  const results = await queryDataBase<ShortUrlRecord[]>(
    "select url, short_url_key as shortKey from short_urls where short_url_key = ?",
    key
  );
  if (results.length > 0) {
    return results[0];
  }
  return null;
}

export async function shortenUrl(url: string): Promise<string> {
  // 生成短链接信息
  const urlInfo = generateShortUrl(url);

  // 判断记录是否已经存在
  let urlEntity = await getShortUrlRecordByFullKey(urlInfo.fullKey);
  if (urlEntity) {
    return urlEntity.shortKey;
  }

  // 检查短 Hash 是否冲突，冲突的话使用全 Hash
  const conflictedEntity = await getShortUrlRecordByShortKey(urlInfo.shortKey);
  if (conflictedEntity) {
    urlInfo.shortKey = urlInfo.fullKey;
  }

  // 保存记录
  await saveShortUrlRecord(urlInfo);
  await cacheEx(urlInfo.shortKey, urlInfo.url, 7 * 24 * 60 * 60);
  return urlInfo.shortKey;
}

export async function parseShortUrl(
  shortUrlKey: string
): Promise<string | null> {
  // 尝试从缓存获取
  const cachedUrl = await getFromCache(shortUrlKey);
  if (cachedUrl) {
    return cachedUrl;
  }

  // 从数据库获取
  const existedRecord = await getShortUrlRecordByShortKey(shortUrlKey);
  return existedRecord?.url || null;
}
