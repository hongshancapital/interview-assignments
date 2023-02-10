import assert from "assert"
import math from '../../../src/libs/math'

const cases: [number, string][] = [
    [1, '1'],
    [10, 'A'],
    [100, '1c'],
    [1000, 'G8'],
    [1879324, '7stg'],
    [6710948712, '7KASKG'],
    [89878177831233, 'PWM3OhE1'],
    [78612039122155, 'MK0YinxT'],
    [981829374140981, '4UneiRgpB']
]

describe('math lib', () => {
    describe('base10to62', () => {
        it('需完成用例列表中所有值的正确转换(10->62)', () => {
            for (const [b10, b62] of cases) {
                assert.equal(math.base10to62(b10), b62)
            }
        })
    })

    describe('base62to10', () => {
        it('需完成用例列表中所有值的正确转换(62->10)', () => {
            for (const [b10, b62] of cases) {
                assert.equal(math.base10to62(b10), b62)
            }
        })
    })
})