import { knex } from 'knex';
import config from 'config';

/**
 * 数据库操作对象
 */
export const db = knex(config.get('dbConfig'));
