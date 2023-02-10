/**
 * model 层的集成测试
 */
import assert from "assert"
import model, { ShortLink } from '../../src/models/shortlink'
import db from '../../src/models/db'
import flag from "../../src/libs/flag"
import Url from "../../src/libs/url"

describe('model integration', () => {
    const url = new Url('https://www.domain.com/a/b/c')
    const version = 1

    after(() => {
        db.client.destroy()
    })

    describe('add', () => {
        it('应能正确添加 url 到数据库中',async () => {
            const link = await model.add(url, version)
    
            // 先检查 result 的正确性
            assert.equal(link.url, url)
            assert.ok(link.id && link.id > 0)
            assert.equal(link.flag, flag.id2flag(link.id as number))
            assert.equal(link.version, version)
    
            // 再检查数据库的正确性
            const id = link.id
    
            let result = await db.select('*').from('shortdomain').where('id', id).first()
            assert.notStrictEqual(result, undefined)
            assert.equal(result['url'], url.url)
    
            // 清理
            await db.table('shortdomain').where('id', id).del()
        })
    })

    describe('getById', () => {
        it('通过 id 查询: 不存在数据应返回 null',async () => {
            const id = 10000012
    
            // 先尝试删掉待测试数据
            await db.table('shortdomain').where('id', id).del()
    
            const link = await model.getById(id)
    
            assert.strictEqual(link, null)
        })
    
        it('通过 id 查询: 存在数据应返回相应的 ShortLink 对象',async () => {
            // 先添加数据
            const added = await model.add(url, version)
            const link = await model.getById(added.id as number)
    
            assert.ok(link instanceof ShortLink)
            assert.equal(link?.id, added.id)
            assert.equal(link?.url.url, added.url.url)
    
            // 清理
            await db.table('shortdomain').where('id', added.id).del()
        })
    })
    
    describe('getByUrl', () => {
        it('根据 url 查询，如果不存在应返回 null',async () => {
            // 先删除待测试数据
            await db.table('shortdomain').where('crc32', url.crc32).andWhere('url', url.url).del()

            const link = await model.getByUrl(url)
            assert.ok(link === null)
        })

        it('根据 url 查询，如果存在应返回相应的 ShortLink 实例',async () => {
            // 先添加测试数据
            const added = await model.add(url, version)

            const link = await model.getByUrl(url)
            assert.ok(link instanceof ShortLink)
            assert.equal(link.id, added.id)
            assert.equal(link.url.url, url.url)

            // 清理
            await db.table('shortdomain').where('crc32', url.crc32).andWhere('url', url.url).del()
        })
    })
})
