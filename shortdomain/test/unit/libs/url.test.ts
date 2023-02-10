import assert from "assert"
import Url from '../../../src/libs/url'
import { InvalidUrlError } from "../../../src/errors"

describe('url util', () => {
    it('合法的url，可正常运行不会抛异常', () => {
        const validUrls = [
            'http://cn',
            'http://i.domain.com/',
            'http://domain.com/',
            'https://i.domain.com/a/b/c',
            'https://i.domain.com/a/b/c.html',
            'https://i.domain.com/a/b/c.html?opt=1',
            'https://i.domain.com/a/b/c.html?opt=1#acher',
            'https://i.domain.com/a/b/c.html?opt=1&name=林子&loves[1]=篮球#acher',
            'https://i.domain.com/a/b/c.html?args=loves%5B1%5D=%E7%AF%AE%E7%90%83#acher'
        ]

        for (const v of validUrls) {
            assert.doesNotThrow(() => {new Url(v)})
        }
    })

    it('非法url，应抛异常', () => {
        const invalidUrls = [
            '',
            'abcd',
            'http:/i.domcin',
            'ws://abc.domain.com/abc/ad',
            'ftp://abc.domain.com',
        ]

        for (const v of invalidUrls) {
            assert.throws(() => new Url(v), InvalidUrlError)
        }
    })
})