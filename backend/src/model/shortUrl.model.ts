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

/**
 * 刷新访问时间，以便定时任务的清理
 * @param id
 */
async function refreshAccessedAt(id: number): Promise<void> {
  await db.update('accessed_at', new Date()).from('short_url').where('id', id);
}

export default {
  listByShortCode,
  save,
  refreshAccessedAt
};
