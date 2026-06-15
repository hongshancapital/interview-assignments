import config from 'config';
import { createPool, ResultSetHeader, RowDataPacket } from 'mysql2/promise';
import logger from '../log/log';

const writePool = createPool({
    host: config.get<string>('mysqlWrite.host'),
    user: config.get<string>('mysqlWrite.user'),
    password: config.get<string>('mysqlWrite.password'),
    database: config.get<string>('mysqlWrite.database'),
    connectionLimit: config.get<number>('mysqlWrite.poolSize'),
    waitForConnections: true,
});

const readPool = createPool({
    host: config.get<string>('mysqlRead.host'),
    user: config.get<string>('mysqlRead.user'),
    password: config.get<string>('mysqlRead.password'),
    database: config.get<string>('mysqlRead.database'),
    connectionLimit: config.get<number>('mysqlRead.poolSize'),
    waitForConnections: true,
});

logger.info('Mysql inited.');

async function mysqlGet(code: string): Promise<string | undefined> {
    logger.debug(`try load from mysql ${code}.`);
    return new Promise<string | undefined>((resolve, reject) => {
        mysqlGetMultiInfo([code])
        .then((rows: any[] | undefined) => {
            if (!rows || rows.length == 0) {
                resolve(undefined);
            } else {
                resolve(rows[0]['origin_url']);
            }
        })
        .catch((err) => {
            reject(err);
        });
    });
}

async function mysqlUpdate(code: string, url: string, timestamp: number, lastUpdateTime: number): Promise<string | undefined> {
    logger.debug(`try update ${code}.`);
    return new Promise<string | undefined>((resolve, reject) => {
        writePool.query<ResultSetHeader>(
            'UPDATE short_url SET origin_url = ?, update_time = ? WHERE short_url = ? and update_time = ?',
            [url, timestamp, code, lastUpdateTime]
        )
        .then((results: [ResultSetHeader, any]) => {
            const [result, _] = results;
            if (result.affectedRows === 1) {
                logger.debug(`update db: ${code}`);
                resolve(code);
            } else {
                resolve(undefined);
            }
        })
        .catch((err) => {
            logger.error(`Update data to mysql failed with ${code}.`, err);
            reject({'status': 500, 'msg': 'Internal server error.'});
        });
    });
}

async function mysqlInsert(code: string, url: string, timestamp: number): Promise<string | undefined> {
    logger.debug(`try insert into db: ${code}`);
    return new Promise<string | undefined>((resolve, reject) => {
        writePool.query<ResultSetHeader>(
            'INSERT INTO short_url (short_url, origin_url, update_time) VALUES (?, ?, ?)',
            [code, url, timestamp]
        )
        .then((results: [ResultSetHeader, any]) => {
            const [result, _] = results;
            if (result.affectedRows === 1) {
                logger.debug(`insert into db: ${code}`);
                resolve(code);
            } else {
                resolve(undefined);
            }
        })
        .catch((err) => {
            if (err.code == 'ER_DUP_ENTRY') {
                resolve(undefined);
            } else {
                logger.error(`Insert data into mysql failed with ${code}.`, err);
                reject({'status': 500, 'msg': 'Internal server error.'});
            }
        });
    });
}

async function mysqlGetMultiInfo(codes: string[]): Promise<any[]> {
    const placeholders = Array(codes.length).fill('?').join(', ');
    return new Promise<any[]>((resolve, reject) => {
        readPool.query<RowDataPacket[]>(
            `SELECT short_url, origin_url, update_time FROM short_url WHERE short_url IN ( ${placeholders} )`,
            codes
        )
        .then((results: [RowDataPacket[], any]) => {
            logger.debug(`get from db: ${codes}`);
            const [rows, _] = results;
            resolve(rows);
        })
        .catch((err) => {
            logger.error(`Load multi data from mysql failed with ${codes}.`, err);
            reject({'status': 500, 'msg': 'Internal server error.'});
        });
    });
}

export { mysqlGet, mysqlInsert, mysqlUpdate, mysqlGetMultiInfo, writePool, readPool };