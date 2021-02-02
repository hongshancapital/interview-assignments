import { injectable } from 'inversify';
import 'reflect-metadata';
import * as mysql from 'mysql2/promise';
import { Pool } from 'mysql2/promise';
import {
  MYSQL_HOST, MYSQL_DBNAME, MYSQL_USER, MYSQL_PWD, SEQUENCE_MAX,
} from '../config';
import { logger } from '../logger';
import { DB_Provider_Intf } from './db_provider_intf';
import { FALSY_RANGE_ID, FALSY_SEQUENCE } from '../constants';

@injectable()
export default class DB_Provider_MySQL implements DB_Provider_Intf {
  private pool: Pool;

  constructor() {
    this.pool = mysql.createPool({
      host: MYSQL_HOST,
      user: MYSQL_USER,
      password: MYSQL_PWD,
      database: MYSQL_DBNAME,
      waitForConnections: true,
    });
    logger.info('DB_Provider_MySQL::initialized');
  }

  raw():Pool {
    // provide this access for test usage only, e.g. to prepare data
    return this.pool;
  }

  async fetch_sequence_of_a_range(range_id:number) : Promise <number> {
    logger.debug(`DB_Provider_MySQL::fetch_sequence_of_a_range() called with range_id='${range_id}'`);
    try {
      const result = await this.pool.execute<mysql.RowDataPacket[]>(
        'select sequence from ranged_sequence where range_id = ? and sequence < ?', [range_id, SEQUENCE_MAX],
      );
      if (result[0].length === 0) { throw new Error('The Range is Full'); }
      return result[0][0].sequence;
    } catch (e) {
      logger.error(`DB_Provider_MySQL::fetch_sequence_of_a_range(): ${e}`);
      return FALSY_SEQUENCE;
    }
  }

  async increase_sequence_of_a_range(range_id:number, sequence:number):Promise<boolean> {
    logger.debug(`DB_Provider_MySQL::increase_sequence_of_a_range() called with range_id='${range_id}' and sequence='${sequence}'`);
    try {
      const res = await this.pool.execute<mysql.OkPacket>(
        'update ranged_sequence set sequence = ? where range_id = ? and sequence = ?', [sequence + 1, range_id, sequence],
      );
      return ((res[0].affectedRows === 1) && (res[0].changedRows === 1));
    } catch (e) {
      logger.error(`DB_Provider_MySQL::increase_sequence_of_a_range(): ${e}`);
      return false;
    }
  }

  async select_a_capable_range_randomly():Promise<number> {
    logger.debug('DB_Provider_MySQL::select_a_capable_range() called');
    try {
      const result = await this.pool.execute<mysql.RowDataPacket []>(
        'select range_id from ranged_sequence where sequence < ? order by rand() limit 1 for update', [SEQUENCE_MAX],
      );
      if (result[0].length === 0) { throw new Error('Cannot Find a Range with Capacity'); }
      return result[0][0].range_id;
    } catch (e) {
      logger.error(`DB_Provider_MySQL::select_a_capable_range_randomly(): ${e}`);
      return FALSY_RANGE_ID;
    }
  }

  async select_a_range_and_increase_its_sequence_transactionally():Promise<number[]> {
    logger.debug('DB_Provider_MySQL::select_and_increase_its_sequence_transactionally() called');
    try {
      const conn = await this.pool.getConnection();
      const result = await this.do_with_transaction(conn);
      conn.release();
      return result;
    } catch (e) {
      logger.error(`DB_Provider_MySQL::select_a_range_and_increase_its_sequence_transactionally(): ${e}`);
      return [FALSY_RANGE_ID, FALSY_SEQUENCE];
    }
  }

  async do_with_transaction(conn:mysql.Connection):Promise<number[]> {
    try {
      await conn.beginTransaction();
      const result = await conn.execute<mysql.RowDataPacket []>(
        'select range_id, sequence from ranged_sequence where sequence < ? order by rand() limit 1 for update',
        [SEQUENCE_MAX],
      );
      if (result[0].length === 0) { throw new Error('Cannot Find a Range with Capacity'); }
      const { range_id, sequence } = result[0][0];
      const res = await conn.execute <mysql.OkPacket>(
        'update ranged_sequence set sequence = ? where range_id = ? and sequence = ?', [sequence + 1, range_id, sequence],
      );
      await conn.commit();
      return ((res[0].affectedRows === 1) && (res[0].changedRows === 1))
        ? [range_id, sequence] : [FALSY_RANGE_ID, FALSY_SEQUENCE];
    } catch (e) {
      logger.error(`DB_Provider_MySQL::do_with_transaction() failed of '${e}'`);
      return [FALSY_RANGE_ID, FALSY_SEQUENCE];
    }
  }

  async stop():Promise<void> {
    await this.pool.end();
  }
}
