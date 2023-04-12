
import { sqliteTable, text } from 'drizzle-orm/sqlite-core';

export const short_urls = sqliteTable('short_urls', {
  short: text('short').primaryKey(),
  long: text('long').notNull(),
});