import dotenv from 'dotenv'
import path from 'path'
dotenv.config({ path: path.resolve(process.cwd(), '.env.test'), debug: true })


import { redisClient, connect, close } from '../connection'
import { BloomFilter } from '../bloomfilter';


describe("test BloomFilter", () => {

    const bloomFilter = new BloomFilter(redisClient)

    beforeAll(async () => {
        await connect()
        if (!await bloomFilter.exist()) {
            await bloomFilter.init()
        }
    })

    afterAll(async () => {
        await bloomFilter.removeAll()
        await close()
    })

    test('test hash add and exist', async () => {
        await bloomFilter.hashAdd('abcd')
        expect(bloomFilter.hashExist('abcd')).resolves.toBeTruthy()
    })

    test('test hash madd and exist', async () => {
        await bloomFilter.hashMAdd(['abcd','asf','sdf'])
        expect(await bloomFilter.hashExist('abcd')).toBeTruthy()
        expect(await bloomFilter.hashExist('asf')).toBeTruthy()
        expect(await bloomFilter.hashExist('sdf')).toBeTruthy()
    })

    test('test url add and exist', async () => {
        await bloomFilter.urlAdd('baidu.com')
        expect(await bloomFilter.urlExist('baidu.com')).toBeTruthy()
    })

    test('test url madd and exist', async () => {
        await bloomFilter.urlMAdd(['baidu.com','google.com','url.cn'])
        expect(await bloomFilter.urlExist('baidu.com')).toBeTruthy()
        expect(await bloomFilter.urlExist('google.com')).toBeTruthy()
        expect(await bloomFilter.urlExist('url.cn')).toBeTruthy()
    })
})