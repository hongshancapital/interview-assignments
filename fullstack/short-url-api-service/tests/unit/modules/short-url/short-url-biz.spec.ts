import biz, { ShortUrlBizError } from '../../../../src/modules/short-url/short-url-biz'
import { assert } from "chai";

describe('modules/short-url/short-url-biz', () => {

    describe('validateGetUrlParams()', () => {
        it('should return error when short url is not match shortUrlBase', () => {
            const params = {
                short_url: 'http://xxx.com/adfd20'
            }
            const shortUrlBase = 'http://yyy.com/'
            assert.strictEqual(biz.validateGetUrlParams(params, shortUrlBase), 'error: short url is in error format')
        })
        it('should return void when short url is match shortUrlBase', () => {
            const params = {
                short_url: 'http://xxx.com/adfd20'
            }
            const shortUrlBase = 'http://xxx.com/'
            assert.isUndefined(biz.validateGetUrlParams(params, shortUrlBase))
        })
    })

    describe('getIdFromShortUrlString()', () => {
        it('should return NaN when short url code invalid', () => {
            const shortUrl = 'http://xxx.com/$adfd20'
            const shortUrlBase = 'http://xxx.com/'
            assert.isNaN(biz.getIdFromShortUrlString(shortUrl, shortUrlBase))
        })
        it('should return parseInt(shortUrl) when short url is not match shortUrlBase', () => {
            const shortUrl = 'http://xxx.com/adfd20'
            const shortUrlBase = 'http://yyy.com/'
            assert.strictEqual(biz.getIdFromShortUrlString(shortUrl, shortUrlBase), parseInt(shortUrl, 36))
        })
        it('should return 100 when short url code is 2s', () => {
            const shortUrlBase = 'http://yyy.com/'
            const shortUrl = shortUrlBase + '2s'
            assert.strictEqual(biz.getIdFromShortUrlString(shortUrl, shortUrlBase), 100)
        })
    })


    describe('validateMaxId()', () => {
        const tooBigMaxId = Math.pow(36, 8)
        it(`should return ${ShortUrlBizError.MAX_ID_LIMIT} when maxId is ${tooBigMaxId}`, () => {
            assert.strictEqual(biz.validateMaxId(tooBigMaxId), ShortUrlBizError.MAX_ID_LIMIT)
        })
        const maxId = 100
        it(`should return undefined when maxId is ${maxId}`, () => {
            assert.isUndefined(biz.validateMaxId(maxId))
        })
    })


})
