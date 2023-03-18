import db from '../db/db';
import ShortUrlEntry from '../entry/shortUrl.entry';

/**
 * 根据shortCode获取长链信息
 * @param shortCode
 */
async function listByShortCode(shortCode: string): Promise<ShortUrlEntry[]> {
  return db
    .select('*')
    .from('short_url')
    .where('short_code', shortCode);
}

/**
 * 新增短链信息
 * @param shortInfo
 */
async function save(shortInfo: object): Promise<void> {
  await db.insert(shortInfo).into('short_url');
}

export default {
  listByShortCode,
  save
};
