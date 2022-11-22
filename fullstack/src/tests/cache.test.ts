import dotenv from 'dotenv'
import path from 'path'
dotenv.config({ path: path.resolve(process.cwd(), '.env.test'), debug: true })


import { redisClient, connect, close } from '../connection'
import { Cache } from '../cache'


describe("test cache", () => {

    const cache = new Cache(redisClient)

    beforeAll(async () => {
        await connect()
    })

    afterAll(async () => {
        await close()
    })

    test('set', async () => {
        const ret: string | null = await cache.set('hash1', 'http://baidu.com')
        expect(ret).toStrictEqual('OK')
    })

    test('get', async () => {
        const ret: string | null = await cache.get('hash1')
        expect(ret).toStrictEqual('http://baidu.com')
    })

    test('lock', async () => {
        jest.useFakeTimers({ legacyFakeTimers: true })

        const lv: string | null = await cache.tryLock('lock1')
        expect(lv).toBeDefined()

        setTimeout(async () => {
            const ok: boolean  = await cache.releaseLock('lock1', lv!)
            expect(ok).toBeTruthy()
        }, 1000)

        setTimeout(async () => {
            const ok: boolean  = await cache.releaseLock('lock1', lv!)
            expect(ok).toBeFalsy()
        }, 3000)

        const lv2: string | null = await cache.tryLock('lock1')
        expect(lv2).toBeNull()

        jest.runOnlyPendingTimers()
        jest.runOnlyPendingTimers()

    })

    test('lock time out', async () => {
        jest.useFakeTimers({ legacyFakeTimers: true })

        const lv: string | null = await cache.tryLock('lock2')
        expect(lv).toBeDefined()

        let p: Promise<string | null> = new Promise((reslove, reject) => {
            setTimeout(() => {
                cache.tryLock('lock2').then(reslove).catch(reject)
            }, 4000)
        })

        jest.runAllTimers()

        const lv2: string | null = await p

        expect(lv2).toBeDefined()
    })

})
