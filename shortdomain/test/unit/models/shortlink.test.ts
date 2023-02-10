/**
 * model 层的单元测试
 * 此处只需要测 ShortLink 的正确性即可，相关数据库交互由集成测试负责
 */
import assert from "assert"
import { ShortLink } from '../../../src/models/shortlink'
import flag from '../../../src/libs/flag'
import Url from "../../../src/libs/url"

describe('ShortLink', () => {
    const url = new Url('https://www.domain.com/a/b/c')

    it('传入非法 id 应抛异常', () => {
        const invalidIds = [-1, 0, flag.MAX_ID + 1]

        for (const v of invalidIds) {
            assert.throws(() => {
                new ShortLink(url, 1, v)
            })
        }
    })

    it('传入非法 version 应抛异常', () => {
        const invalidVersions = [-1, 0, flag.MAX_VERSION + 1]

        for (const v of invalidVersions) {
            assert.throws(() => {
                new ShortLink(url, v, 1)
            })
        }
    })

    it('flag 应符合预期', () => {
        // id, version
        const ids: [number, number][] = [
            [1, 1],
            [1, 2],
            [10, 1],
            [1000, 2],
            [192344, flag.MAX_VERSION]
        ]

        // id 不存在时应返回空字符串
        assert.equal(new ShortLink(url, 1).flag, '')

        for (const [id, version] of ids) {
            const link = new ShortLink(url, version, id)
            assert.equal(link.flag, flag.id2flag(id, version))
        }
    })
})