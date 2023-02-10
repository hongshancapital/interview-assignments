/**
 * cache 层的集成测试，主要测试各操作执行后 Redis 中的数据是否符合预期
 */
import assert from "assert"
import redis from '../../src/libs/rdclient'
import cache, { integrationTestInit } from '../../src/libs/cache'
import Url from "../../src/libs/url"
import { ShortLink } from "../../src/models/shortlink"

const keyInfo = integrationTestInit()
const url = new Url('https://www.domain.cn/a/b/c')
const link = new ShortLink(url, 1, 123456)
const key = keyInfo.flagKeyPrefix + link.flag

describe('cache integration', () => {
    after(async () => {
        await redis.del(keyInfo.BL_KEY_FLAG)
        await redis.del(keyInfo.BL_KEY_URL)

        redis.disconnect()
    })

    beforeEach(async () => {
        await redis.del(keyInfo.BL_KEY_FLAG)
        await redis.del(keyInfo.BL_KEY_URL)
    })

    describe('isFlagInBloom', () => {
        it('bloom 过滤器的判断应符合预期', async () => {
            const flag = link.flag

            assert.strictEqual(await cache.isFlagInBloom(flag), false)
            await redis.call('BF.ADD', keyInfo.BL_KEY_FLAG, flag)
            assert.strictEqual(await cache.isFlagInBloom(flag), true)
        })
    })

    describe('isUrlInBloom', () => {
        it('bloom 过滤器的判断应符合预期', async () => {
            assert.strictEqual(await cache.isUrlInBloom(url), false)
            await redis.call('BF.ADD', keyInfo.BL_KEY_URL, url.crc32)
            assert.strictEqual(await cache.isUrlInBloom(url), true)
        })
    })

    describe('get', () => {
        it('存在缓存，应返回值，并延长有效期', async () => {
            await redis.set(key, link.url.url, 'EX', keyInfo.ttl)
            
            // 等待 3 秒
            await new Promise(resolve => {
                setTimeout(resolve, 3000)
            })

            const result = await cache.get(link.flag)
            // 拿 ttl
            const ttl = await redis.ttl(key)
            
            assert.equal(result.cacheVal, link.url.url)
            // 期望 ttl 很接近 keyInfo.ttl
            assert.ok(keyInfo.ttl - ttl < 3)

            // 清理
            await redis.del(key)
        })

        it('不存在缓存，bloom 过滤器的表现应符合预期', async () => {
            await redis.del(key)

            const before = await cache.get(link.flag)
            assert.equal(before.cacheVal, '')
            assert.equal(before.maybeExists, false)

            // 添加布隆过滤器
            await redis.call('BF.ADD', keyInfo.BL_KEY_FLAG, link.flag)

            const after = await cache.get(link.flag)
            assert.equal(after.cacheVal, '')
            assert.equal(after.maybeExists, true)
        })
    })

    describe('add', () => {
        it('应正确生成缓存和两个布隆过滤器',async () => {
            // 先删除 key
            await redis.del(key)

            await cache.add(link.flag, url)

            assert.equal(await redis.get(key), url.url)
            assert.equal(await redis.call('BF.EXISTS', keyInfo.BL_KEY_FLAG, link.flag), 1)
            assert.equal(await redis.call('BF.EXISTS', keyInfo.BL_KEY_URL, url.crc32), 1)

            // 清理
            await redis.del(key)
        })
    })
})