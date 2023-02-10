import assert from "assert"
import sinon from 'sinon'
import cache from "../../../src/libs/cache"
import client from "../../../src/libs/rdclient"
import Url from "../../../src/libs/url"

describe('cache lib', () => {
    afterEach(() => {
        sinon.restore()
    })

    const flag = 'testflag'
    const url = new Url('http://www.baidu.com')

    describe('isFlagInBloom,isUrlInBloom', () => {
        it('当 Redis 返回 0 时，函数返回 false', async () => {
            sinon
            .stub(client, 'call')
            .callsFake(async (cmd: string, ...args: (string | number | Buffer)[]) => {
                return 0
            })

            assert.equal(await cache.isFlagInBloom(flag), false)
            assert.equal(await cache.isUrlInBloom(url), false)
        })

        it('当 Redis 返回 1 时，函数返回 true', async () => {
            const flag = 'testflag'

            sinon
            .stub(client, 'call')
            .callsFake(async (cmd: string, ...args: (string | number | Buffer)[]) => {
                return 1
            })

            assert.equal(await cache.isFlagInBloom(flag), true)
            assert.equal(await cache.isUrlInBloom(url), true)
        })
    })

    describe('get', () => {
        it('当 Redis 返回 0 时 表示 bloom 过滤器不存在该值', async () => {
            sinon
            .stub(client, 'eval')
            .callsFake(async (script: string | Buffer, ...args: (string | number | Buffer)[]) => {
                return 0
            })

            const result = await cache.get(flag)
            assert.equal(result.cacheVal, '')
            assert.equal(result.maybeExists, false)
        })

        it('当 Redis 返回 1 时 表示 bloom 过滤器存在该值，但缓存中不存在值', async () => {
            sinon
            .stub(client, 'eval')
            .callsFake(async (script: string | Buffer, ...args: (string | number | Buffer)[]) => {
                return 1
            })

            const result = await cache.get(flag)
            assert.equal(result.cacheVal, '')
            assert.equal(result.maybeExists, true)
        })

        it('当 Redis 返回 url 字符串时缓存中应存在值', async () => {
            sinon
            .stub(client, 'eval')
            .callsFake(async (script: string | Buffer, ...args: (string | number | Buffer)[]) => {
                return url.url
            })

            const result = await cache.get(flag)
            assert.equal(result.cacheVal, url.url)
            assert.equal(result.maybeExists, true)
        })
    })

    describe('add', () => {
        it('应直接返回 redis 的执行结果', async () => {
            let stub = sinon.stub(client, 'eval')

            stub.callsFake(async (script: string | Buffer, ...args: (string | number | Buffer)[]) => {
                return 0
            })

            let result = await cache.add(flag, url)
            assert.equal(result, 0)

            stub.restore()
            stub = sinon.stub(client, 'eval')

            stub.callsFake(async (script: string | Buffer, ...args: (string | number | Buffer)[]) => {
                return 1
            })

            result = await cache.add(flag, url)
            assert.equal(result, 1)
        })
    })
})