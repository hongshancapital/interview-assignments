import { RowDataPacket, OkPacket, createConnection } from "mysql2";
import { encodeID, decodeID, splitURL } from './utils';

const conn = createConnection({
    host: process.env.DB_HOST,
    port: process.env.DB_PORT as unknown as number,
    user: process.env.DB_USER,
    password: process.env.DB_PASS,
    database: process.env.DB_NAME
})

interface IShortLink extends RowDataPacket {
    id?: number
    hash?: string
    domain: string
    path: string
    domain_crc32?: number
    path_crc32?: number
}

export class ShortLinkRepository {

    // 通过 crc32 哈希建立的索引加快查找速度
    async findByUrl(domain: string, path: string): Promise<IShortLink | undefined> {
        let [rows, fields] = await conn.promise().execute<IShortLink[]>("SELECT * from shortlinks where domain_crc32 = crc32(?) and path_crc32 = crc32(?) and domain = ? and path = ?",
            [domain, path, domain, path])
        return rows?.[0]
    }

    async findById(id: number): Promise<IShortLink | undefined> {
        let [rows, fields] = await conn.promise().execute<IShortLink[]>("SELECT * from shortlinks where id = ?", [id])
        return rows?.[0]
    }

    // 插入时只有 domain 与 path
    async create(shortlink: IShortLink): Promise<IShortLink | undefined> {
        let [res, fields] = await conn.promise().execute<OkPacket>("INSERT INTO shortlinks (domain,path,domain_crc32,path_crc32) values (?,?,crc32(?),crc32(?))",
            [shortlink.domain, shortlink.path, shortlink.domain, shortlink.path])
        if (res.insertId) {
            return await this.findById(res.insertId)
        } else {
            return undefined
        }
    }

    // 只会更新 hash 值
    async update(shortlink: IShortLink): Promise<IShortLink | undefined> {
        let [res, fields] = await conn.promise().execute<OkPacket>("UPDATE shortlinks SET hash = ? where id = ? ", [shortlink.hash, shortlink.id])
        if (res.changedRows = 1) {
            return await this.findById(shortlink.id!)
        } else {
            return undefined
        }
    }
}