/**
 * 初始化Mysql链接等操作
 */
import * as mysql from 'mysql2';
import Pool from 'mysql2/typings/mysql/lib/Pool';
import { config } from '../config/dbConfig';
export const pool: Pool = mysql.createPool(
    config
);
