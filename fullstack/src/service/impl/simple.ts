import { ShortLinkParams, ShortLinkServiceBase } from '../base';
import pg, { DatabaseError } from 'pg';

import { idToStr, strToId, compress, uncompress, converts, isID } from '../../utils';

export { DatabaseError };

/**
 * 简单的实现，PG序列转换为62进制字符，左侧补0至8位
 * 单库单表可支持亿级记录
 */
export class Simple extends ShortLinkServiceBase {
  pool: pg.Pool;
  constructor(params: ShortLinkParams) {
    super(params);
    const cfg: any = {};

    const keys: any = {
      max: converts.number,
      min: converts.number,
      idleTimeoutMillis: converts.number,
      allowExitOnIdle: converts.boolean,
      maxUses: converts.number,
      user: converts.string,
      database: converts.string,
      password: converts.string,
      port: converts.number,
      host: converts.string,
      connectionString: converts.string,
      keepAlive: converts.boolean,
      parseInputDatesAsUTC: converts.boolean,
      ssl: converts.boolean,
      query_timeout: converts.number,
      keepAliveInitialDelayMillis: converts.number,
      idle_in_transaction_session_timeout: converts.number,
      application_name: converts.string,
      connectionTimeoutMillis: converts.number,
      options: converts.string,
    };

    Object.keys(params).forEach((key) => {
      if (key.indexOf('pg_') === 0) {
        const _key = key.substring(3);

        if (keys[_key]) {
          cfg[_key] = keys[_key](params[key]);
        }
      }
    });

    this.pool = new pg.Pool(cfg);
  }
  async _newId(db: pg.PoolClient): Promise<bigint> {
    const data = await db.query("select nextval('sl_id_seq') as nextval");

    return BigInt(data.rows[0]?.nextval);
  }
  async _createRecord(db: pg.PoolClient, record: { id: string; link: string }): Promise<void> {
    await db.query('insert into sl_data (id, link) values ($1, $2)', [record.id, record.link]);
  }
  async saveLink(link: string): Promise<string> {
    const db = await this.pool.connect();

    try {
      const newId = await this._newId(db);
      const strId = idToStr(newId, { pos: 'left', char: '0', len: 8 });

      await this._createRecord(db, {
        id: strId,
        link: compress(link),
      });
      return this.formatLink(strId);
    } finally {
      await db.release();
    }
  }
  async getLink(id: string): Promise<string> {
    if (!isID(id)) {
      throw new Error('id is invalid');
    }
    const db = await this.pool.connect();

    try {
      const data = await db.query('select link from sl_data where id = $1', [id]);

      return data.rows.length ? uncompress(data.rows[0]?.link) : '';
    } finally {
      await db.release();
    }
  }
}
