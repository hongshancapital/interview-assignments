import { ResultSetHeader, RowDataPacket } from 'mysql2';
import db from './db.js';
import cache from './cache.js';

interface originUrl extends RowDataPacket {
    id: number;
    url: string;
}

class OriginUrlModel {
    private static KEY_LONG_TO_SHORT_PREFIX: string = 'url:longToShort:';
    private static KEY_SHORT_TO_LONG_PREFIX: string = 'url:shortToLong:';
    private static TTL: number = 300;

    private getLongToShortKey(long: string): string {
        return OriginUrlModel.KEY_LONG_TO_SHORT_PREFIX + long;
    }

    private getShortToLongKey(short: string): string {
        return OriginUrlModel.KEY_SHORT_TO_LONG_PREFIX + short;
    }

    cacheLongToShort(long: string, short: string): void {
        const key = this.getLongToShortKey(long);
        cache.set(key, short, { EX: OriginUrlModel.TTL });
    }

    cacheShortToLong(short: string, long: string): void {
        const key = this.getShortToLongKey(short);
        cache.set(key, long, { EX: OriginUrlModel.TTL });
    }

    async getLongFromCache(short: string): Promise<string | null> {
        const key = this.getShortToLongKey(short);
        return await cache.get(key);
    }

    async getShortFromCache(long: string): Promise<string | null> {
        const key = this.getLongToShortKey(long);
        return await cache.get(key);
    }

    async getIdByLongUrl(url: string): Promise<number | null> {
        const [rows] = await db.query<originUrl[]>('select id from origin_url where url = ?', [url]);
        if (rows && rows.length) {
            return rows[0].id;
        }
        return null;
    }

    async getLongUrlById(id: number): Promise<string | null> {
        const [rows] = await db.query<originUrl[]>('select url from origin_url where id = ?', [id]);
        if (rows && rows.length) {
            return rows[0].url;
        }
        return null;
    }

    async addUrl(url: string): Promise<number> {
        const [result] = await db.query<ResultSetHeader>('insert ignore into origin_url(url) values(?)', [url]);
        return result.insertId;
    }
}

export default new OriginUrlModel();
