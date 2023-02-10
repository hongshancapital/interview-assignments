import assert from "assert";
import flag from '../../../src/libs/flag'

// [id, version, flag]
const cases:[number, number, string][] = [
    [1, 1, '11'],
    [1, 2, '1H'],
    [10, 1, 'AA'],
    [100, 1, '1c2'],
    [100, 3, '1cY'],
    [10918731, 1, 'joSZ5'],
    [10918731, 3, 'joSZb'],
    [1091873187, 3, '1BtO5rj'],
]

describe('flag lib', () => {
    describe('flag2id', () => {
        it('flag格式不合法，应抛异常', () => {
            const invalids = ['ab_d', '*&78-', 'abcdefgd8', '1BtO5rK', 'joSZY', 'joSZ', '1c1']
            for (const v of invalids) {
                assert.throws(() => flag.flag2id(v))
            }
        })

        it('应正确完成用例列表中值的转换', () => {
            for (const [id, _, flg] of cases) {
                assert.equal(flag.flag2id(flg), id)
            }
        })
    })

    describe('id2flag', () => {
        it('id 值超过范围，应抛异常', () => {
            assert.throws(() => flag.id2flag(flag.MAX_ID + 1))
            assert.throws(() => flag.id2flag(0))
            assert.throws(() => flag.id2flag(-1))
        })
    
        it('版本号超过范围，应抛异常', () => {
            assert.throws(() => flag.id2flag(flag.MAX_ID, flag.MAX_VERSION + 1))
            assert.throws(() => flag.id2flag(flag.MAX_ID, 0))
            assert.throws(() => flag.id2flag(flag.MAX_ID, -1))
        })
    
        it('应正确完成用例列表中值的转换', () => {
            for (const [id, version, flg] of cases) {
                assert.equal(flag.id2flag(id, version), flg)
            }
        })
    })
})