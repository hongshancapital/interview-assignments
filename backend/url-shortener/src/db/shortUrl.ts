import { getDb } from './db';

/**
 * 短域名表名
 */
const SHORT_URL_TABLE = 'shortUrls';

/**
 * 创建短域名表（不存在则创建）
 * shortCode 字段为主键
 * longUrl 长域名 唯一约束
 */
async function createShortUrlTable() {
    await getDb().schema.createTableIfNotExists(SHORT_URL_TABLE, (table) => {
        // 主键目前8位即可，预留空间
        table.string('shortCode', 20).primary();
        table.string('longUrl', 3000).unique();
    });
}

export { SHORT_URL_TABLE, createShortUrlTable };
