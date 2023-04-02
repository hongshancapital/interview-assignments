import * as mysql from 'mysql2/promise';
import { OkPacket, RowDataPacket } from 'mysql2/promise';
const conf = {
    host: '127.0.0.1',
    user: 'root',
    password: '123456',
    database: 'test',
}

interface DomainNameStruct {
    id: number,
    longName: string,
    shortName: string,
}

export const poolConfig = {
    ...conf,
    waitForConnections: true,
    connectionLimit: 10,
    queueLimit: 0,
};

export const pool = mysql.createPool(poolConfig);

export async function getOneConnection(): Promise<mysql.PoolConnection> {
    return pool.getConnection();;
}

export async function insertDomainNameInMySql(longName: string, conn?: mysql.PoolConnection) {
    const sql = 'INSERT INTO domainNames (longName) VALUES (?)';
    const [result] = await (conn ? conn.execute(sql, [longName]) : pool.execute(sql, [longName]));
    return (result as OkPacket).insertId;
}

export async function updateShortCodeInMySql(id: number, shortName: string, conn?: mysql.PoolConnection) {
    const query = 'UPDATE domainNames SET shortName = ? WHERE id = ?';
    const results = await (conn ? conn.execute(query, [shortName, id]) : pool.execute(query, [shortName, id]));
    return results;
}

export async function getDomainInfoByLongName(longDomainName: string, conn?: mysql.PoolConnection) {
    const query = 'SELECT * FROM domainNames WHERE longName = ?';
    const [rows] = await (conn ? conn.execute(query, [longDomainName]) : pool.execute(query, [longDomainName]));
    if ((rows as RowDataPacket[]).length === 0) {
        return undefined;
    }
    const domain = (rows as RowDataPacket[])[0] as DomainNameStruct;
    return domain;
}

export async function getDomainInfoById(id: number, conn?: mysql.PoolConnection): Promise<DomainNameStruct | undefined>{
    const query = 'SELECT * FROM domainNames WHERE id = ?';
    const [rows] = await (conn ? conn.execute(query, [id]) : pool.execute(query, [id]));
    if ((rows as RowDataPacket[]).length === 0) {
        return undefined;
    }
    const domain = (rows as RowDataPacket[])[0] as DomainNameStruct;
    return domain;
}