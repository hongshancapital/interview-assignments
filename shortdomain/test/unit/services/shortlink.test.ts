import assert from 'assert'
import sinon from 'sinon'
import shortlink from '../../../src/services/shortlink'
import cache, { CacheResult } from '../../../src/libs/cache'
import model, { ShortLink } from '../../../src/models/shortlink'
import Url from '../../../src/libs/url'

describe('shortlink service', () => {
    afterEach(() => {
        sinon.restore()
    })

    const url = new Url('https://www.baidu.com/a/b')
    const version = 1
    const id = 10918731
    const flag = 'joSZ5'

    describe('genShortLink', () => {
        it('非法的 url, 应该抛异常', async () => {
            const invalidUrls = [
                '',
                'abc',
                'http:/abdc.om',
                'ftp://abdc.om'
            ]

            for (const v of invalidUrls) {
                assert.rejects(async () => {
                    await shortlink.genShortLink(v)
                })
            }
        })

        it('bloom 过滤器告知不存在，则应生成新 shortlink 写入到数据库和缓存中', async () => {
            sinon.stub(cache, 'isUrlInBloom').callsFake(async (url) => {
                return false
            })
            
            const spyModel = sinon.stub(model, 'add').callsFake(async (url: Url, v: number) => {
                return new ShortLink(url, version, id)
            })
            const spyCache = sinon.stub(cache, 'add')

            const flg = await shortlink.genShortLink(url.url)

            assert.equal(spyModel.calledOnce, true)
            assert.equal(spyCache.calledOnce, true)
            assert.equal(flg, flag)
        })

        it('bloom 过滤器告知可能存在，但数据库中不存在，则应生成新 shortlink 写入到数据库和缓存中', async () => {
            sinon.stub(cache, 'isUrlInBloom').callsFake(async (url) => {
                return true
            })

            const spyDbQuery = sinon.stub(model, 'getByUrl').callsFake(async (url: Url) => {
                return null
            })
            const spyDbAdd = sinon.stub(model, 'add').callsFake(async (url: Url, v: number) => {
                return new ShortLink(url, version, id)
            })
            const spyCache = sinon.stub(cache, 'add')

            const flg = await shortlink.genShortLink(url.url)

            assert.equal(spyDbAdd.calledOnce, true)
            assert.equal(spyDbQuery.calledOnce, true)
            assert.equal(spyCache.calledOnce, true)
            assert.equal(flg, flag)
        })

        it('bloom 过滤器告知存在，且数据库中也存在，则直接返回数据库的', async () => {
            sinon.stub(cache, 'isUrlInBloom').callsFake(async (url) => {
                return true
            })

            const spyDbAdd = sinon.stub(model, 'add')
            const spyDbQuery = sinon.stub(model, 'getByUrl').callsFake(async (url: Url) => {
                return new ShortLink(url, version, id)
            })

            const flg = await shortlink.genShortLink(url.url)

            assert.equal(spyDbAdd.notCalled, true)
            assert.equal(spyDbQuery.calledOnce, true)
            assert.equal(flg, flag)
        })
    })

    describe('getUrl', async () => {
        function stubCacheGet(result: CacheResult) {
            sinon.stub(cache, 'get').callsFake(async (flag) => {
                return result
            })
        }

        it('flag 格式不合法，应抛异常', async () => {
            const invalidFlags = [
                '',
                'ad',
                'afa09_d',
                '7dyuadf',
                'asfdasiyJHwd',
            ]

            for (const v of invalidFlags) {
                assert.rejects(async () => {
                    await shortlink.getUrl(v)
                })
            }
        })

        it ('存在缓存，应直接返回缓存数据', async () => {
            stubCacheGet({
                maybeExists: true,
                cacheVal: url.url
            })

            const dbSpy = sinon.stub(model, 'getById')
            const result = await shortlink.getUrl(flag)

            assert.equal(dbSpy.notCalled, true)
            assert.equal(result, url.url)
        })

        it ('没有缓存，且 bloom 过滤器告知不存在，则直接返回空字符串', async () => {
            stubCacheGet({
                maybeExists: false,
                cacheVal: ''
            })

            const dbSpy = sinon.stub(model, 'getById')
            const result = await shortlink.getUrl(flag)

            assert.equal(dbSpy.notCalled, true)
            assert.equal(result, '')
        })

        it ('没有缓存，且 bloom 过滤器告知可能存在，但数据库中不存在，则返回空字符串', async () => {
            stubCacheGet({
                maybeExists: true,
                cacheVal: ''
            })

            let dbStub = sinon.stub(model, 'getById').callsFake(async (id) => {
                return null
            })

            const result = await shortlink.getUrl(flag)

            assert.equal(dbStub.calledOnce, true)
            assert.equal(result, '')
        })

        it ('没有缓存，且 bloom 过滤器告知可能存在，且数据库中存在，则返回数据库中的值', async () => {
            stubCacheGet({
                maybeExists: true,
                cacheVal: ''
            })

            let dbStub = sinon.stub(model, 'getById').callsFake(async (id) => {
                return new ShortLink(url, version, id)
            })

            const result = await shortlink.getUrl(flag)

            assert.equal(dbStub.calledOnce, true)
            assert.equal(result, url.url)
        })
    })
})
