import {verbose,Database} from 'sqlite3';

const sqlite3 = verbose();
const db: Database = new sqlite3.Database('./work.db');

export const query = () => {
  return new Promise((resolve, reject) => {
    db.serialize(() => {
      db.all('select * from url_map', (err, rows) => {
        if (err) {
          reject(err);
        } else {
          resolve(rows);
        }
      });
    });
  });
}