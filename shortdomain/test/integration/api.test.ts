/**
 * API 集成测试
 * 注意：执行 API 的集成测试需保证 Redis、MySQL 实际可用
 */
import assert from "assert"
import request from 'supertest'
import app from '../../src/app'
import ErrCode from '../../src/config/code'
import { integrationTestInit } from '../../src/libs/cache'
import Url from "../../src/libs/url"
import flag from '../../src/libs/flag'
import redis from '../../src/libs/rdclient'
import db from '../../src/models/db'

// 需手动操作 cache 来控制测试
const keyInfo = integrationTestInit()
const url = new Url('http://www.domain.com/a/b/c/7189817?name=林子#abcd')

/**
 * 清理集成测试用的相关 Redis 和 MySQL 数据
 */
async function reset() {
    // 删除布隆过滤器
    await redis.del(keyInfo.BL_KEY_FLAG)
    await redis.del(keyInfo.BL_KEY_URL)

    const results = await db.select(['id', 'url', 'crc32', 'version']).from('shortdomain').where(url)
    for (const result of results) {
        // 删除缓存
        await redis.del(`${keyInfo.flagKeyPrefix}:${flag.id2flag(result.id)}`)
    }

    // 删数据库数据
    await db.table('shortdomain').where(url).del()
}

describe('api integration', () => {
    before(reset)
    after(reset)

    describe('GET /links/:flag', () => {
        it('未提供 flag 参数，应报 NOT_FOUND 错误',async () => {
            const resp = await request(app).get('/links')
            assert.equal(resp.body.code, ErrCode.NOT_FOUND)
        })

        it('提供的 flag 不合法，应报 INVALID_FLAG 错误',async () => {
            const invalidFlags = [
                'abc',
                'dd',
                '3672F',
                '78dFFDA3aa'
            ]

            for (const v of invalidFlags) {
                assert.equal((await request(app).get(`/links/${v}`)).body.code, ErrCode.INVALID_FLAG)
            }
        })

        it('查不存在的 flag 应返回 NOT_FOUND',async () => {
            const id = 1000
            const flg = flag.id2flag(id)

            // 删掉
            await db.table('shortdomain').where('id', id).del()
            // 清缓存
            await redis.del(`${keyInfo.flagKeyPrefix}:${flg}`)

            assert.equal((await request(app).get(`/links/${flg}`)).body.code, ErrCode.NOT_FOUND)
        })
    })

    describe('POST /links', () => {
        it('缺少参数，应返回 INVALID_PARAMS 错误', async () => {
            assert.equal((await request(app).post('/links')).body.code, ErrCode.INVALID_PARAMS)
        })

        it('传入非法的 url 参数，应返回 INVALID_URL 错误', async () => {
            assert.equal((await request(app).post('/links').send('url=abc')).body.code, ErrCode.INVALID_URL)
        })

        it('传入合法 url，应生成并返回 flag',async () => {
            const addResp = (await (await request(app).post('/links').send(`url=${encodeURI(url.url)}`))).body
            assert.equal(addResp.code, ErrCode.OK)
            
            const flg = addResp.data.short
            assert.doesNotThrow(() => flag.flag2id(flg))

            // 查询
            const queryResp = (await request(app).get(`/links/${flg}`)).body
            assert.equal(queryResp.code, ErrCode.OK)
            assert.equal(queryResp.data.url, url.url)
        })
    })
})