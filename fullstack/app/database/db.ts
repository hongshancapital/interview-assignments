import path from 'path';
import {verbose,Database} from 'sqlite3';
import { DataModal } from '..';
import { SUCCESS, PARAM_ERROR } from '../common/errCode';

const sqlite3 = verbose();

const db: Database = new sqlite3.Database(path.join(__dirname + '../../../work.db'));

export const query = (data: DataModal = {}) : Promise<Array<DataModal>> => {
  const { short_url, origin_md5 } = data;

  let sql: string = 'select * from url_map where ';

  if (short_url) {
    sql += '`short_url` = ' + `'${short_url}'`;
  } else if (origin_md5) {
    sql += '`origin_md5` = ' + `'${origin_md5}'`;
  } else {
    return Promise.reject(PARAM_ERROR);
  }

  return new Promise((resolve, reject) => {
    db.all(sql, (err: Error | null, rows: Array<DataModal>) => {
      if (err) {
        reject(err);
      } else {
        resolve(rows);
      }
    });
  });
}

export const insert = (data: DataModal = {}) : Promise<Array<DataModal> | string> => {
  const { short_url, origin_url, origin_md5 } = data;

  if (!short_url || !origin_url || !origin_md5) {
    return Promise.reject(PARAM_ERROR);
  }

  const sql = 'insert into url_map (`short_url`, `origin_url`, `origin_md5`) values (?, ?, ?)';
  
  return new Promise((resolve, reject) => {
    db.run(sql, [short_url, origin_url, origin_md5], ( err: Error | null ) => {
      if (err) {
        reject(PARAM_ERROR);
      } else {
        resolve(SUCCESS);
      }
    });
  });
}

export const remove = (data: DataModal = {}) : Promise<Array<DataModal> | string> => {
  const { short_url, origin_md5 } = data;

  let sql: string = 'delete from url_map where ';

  if (short_url) {
    sql += '`short_url` = ' + `'${short_url}'`;
  } else if (origin_md5) {
    sql += '`origin_md5` = ' + `'${origin_md5}'`;
  } else {
    return Promise.reject(PARAM_ERROR);
  }
  
  return new Promise((resolve, reject) => {
    db.run(sql, ( err: Error | null ) => {
      if (err) {
        reject(PARAM_ERROR);
      } else {
        resolve(SUCCESS);
      }
    });
  });
}