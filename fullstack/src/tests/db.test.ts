import { OkPacket } from 'mysql2';
import dotenv from 'dotenv'
import path from 'path'
dotenv.config({ path: path.resolve(process.cwd(), '.env.test'), debug: true })

import { mysqlConnection, connect, close } from '../connection';
import { ShortLinkRepository, IShortLink, createShortLink } from '../db';
import { encodeID } from '../utils';

const initdb = async () => {
    const [ok, fields] = await mysqlConnection.promise().query<OkPacket>('CREATE TABLE `shortlinks` (`id` int unsigned NOT NULL AUTO_INCREMENT,' +
        '`hash` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,' +
        '`domain` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,' +
        '`path` varchar(8182) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,' +
        '`domain_crc32` bigint NOT NULL,' +
        '`path_crc32` bigint NOT NULL,' +
        'PRIMARY KEY (`id`),' +
        'KEY `shortlink_crc32` (`domain_crc32`,`path_crc32`)' +
        ') ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;')
}

const cleardb = async () => {
    try {
        const [ok, fields] = await mysqlConnection.promise().query<OkPacket>('DROP TABLE `shortlinks`')
    } catch (e) {

    }
}

describe('test db', () => {
    const slr: ShortLinkRepository = new ShortLinkRepository(mysqlConnection)
    beforeAll(async () => {
        await connect()
        await cleardb()
        await initdb()
    })
    afterAll(async () => {
        await cleardb()
        await close()
    })

    test('add shortlink', async () => {
        let sl: IShortLink = createShortLink({
            domain: 'http://baidu.com',
            path: '/'
        })
        let is: IShortLink | undefined = await slr.create(sl)
        expect(is).toBeDefined()
        expect(is?.domain).toStrictEqual('http://baidu.com')
        expect(is?.path).toStrictEqual('/')
    })

    test('find by id', async () => {
        let is: IShortLink | undefined = await slr.findById(1)
        expect(is).toBeDefined()
        expect(is?.domain).toStrictEqual('http://baidu.com')
        expect(is?.path).toStrictEqual('/')
    })

    test('find by url', async () => {
        let is: IShortLink | undefined = await slr.findByUrl('http://baidu.com', '/')
        expect(is).toBeDefined()
        expect(is?.domain).toStrictEqual('http://baidu.com')
        expect(is?.path).toStrictEqual('/')
    })

    test('update', async () => {
        let is: IShortLink | undefined = await slr.findById(1)
        let hash = encodeID(is!.id!)
        is!.hash = hash
        let is2 = await slr.update(is!)
        expect(is2).toBeDefined()
        expect(is2?.domain).toStrictEqual('http://baidu.com')
        expect(is2?.path).toStrictEqual('/')
        expect(is2?.hash).toStrictEqual(hash)
    })

    test('count', async () => {
        let count = await slr.count()
        expect(count).toBe(1)

        for (let i = 0; i < 19; i++) {
            let sl: IShortLink = createShortLink({
                domain: `http://baidu${i}.com`,
                path: '/'
            })
            let is: IShortLink | undefined = await slr.create(sl)
            expect(is).toBeDefined()
            expect(is?.domain).toStrictEqual(`http://baidu${i}.com`)
        }

        count = await slr.count()
        expect(count).toBe(20)
    })

    test('page', async () => {
        let sls = await slr.findByPage(0, 10)
        expect(sls.length).toBe(10)
        sls = await slr.findByPage(1, 5)
        expect(sls.length).toBe(5)
        sls = await slr.findByPage(6, 3)
        expect(sls.length).toBe(2)
    })

})