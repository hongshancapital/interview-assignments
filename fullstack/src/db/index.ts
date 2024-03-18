

import { drizzle } from 'drizzle-orm/better-sqlite3';
import Database from 'better-sqlite3';

export * from './schema';

export const sqlite = new Database('sqlite.db');
export const conn = drizzle(sqlite);
