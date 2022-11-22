import { RowDataPacket, OkPacket, Connection } from 'mysql2';

export interface IShortLink extends RowDataPacket {
    id?: number
    hash?: string
    domain: string
    path: string
    domain_crc32?: number
    path_crc32?: number
}

export class ShortLinkRepository {
    private conn: Connection

    constructor(conn: Connection) {
        this.conn = conn
    }

    async findByPage(page: number, size: number): Promise<IShortLink[]> {
        let [rows, fields] = await this.conn.promise().query<IShortLink[]>('SELECT * FROM `shortlinks` LIMIT ? OFFSET ?', [size, page * size])
        return rows
    }

    async count(): Promise<number> {
        let [rows, fields] = await this.conn.promise().query<RowDataPacket[]>('SELECT count(id) AS id_count FROM `shortlinks`')
        return rows[0].id_count
    }

    // 通过 crc32 哈希建立的索引加快查找速度
    async findByUrl(domain: string, path: string): Promise<IShortLink | undefined> {
        let [rows, fields] = await this.conn.promise().execute<IShortLink[]>('SELECT * FROM `shortlinks` WHERE `domain_crc32` = crc32(?) AND `path_crc32` = crc32(?) AND `domain` = ? AND `path` = ?',
            [domain, path, domain, path])
        return rows?.[0]
    }

    async findById(id: number): Promise<IShortLink | undefined> {
        let [rows, fields] = await this.conn.promise().execute<IShortLink[]>('SELECT * FROM `shortlinks` WHERE id = ?', [id])
        return rows?.[0]
    }

    // 插入时只有 domain 与 path
    async create(shortlink: IShortLink): Promise<IShortLink | undefined> {
        let [res, fields] = await this.conn.promise().execute<OkPacket>('INSERT INTO `shortlinks` (`domain`,`path`,`domain_crc32`,`path_crc32`) VALUES (?,?,crc32(?),crc32(?))',
            [shortlink.domain, shortlink.path, shortlink.domain, shortlink.path])
        if (res.insertId) {
            return await this.findById(res.insertId)
        } else {
            return undefined
        }
    }

    // 只会更新 hash 值
    async update(shortlink: IShortLink): Promise<IShortLink | undefined> {
        let [res, fields] = await this.conn.promise().execute<OkPacket>('UPDATE shortlinks SET hash = ? where id = ? ', [shortlink.hash, shortlink.id])
        if (res.changedRows = 1) {
            return await this.findById(shortlink.id!)
        } else {
            return undefined
        }
    }
}

export declare type ShortLink = {
    domain: string
    path: string
}

export function createShortLink(shortlink: ShortLink): IShortLink {
    return {
        constructor: {
            name: 'RowDataPacket'
        },
        domain: shortlink.domain,
        path: shortlink.path
    }
}