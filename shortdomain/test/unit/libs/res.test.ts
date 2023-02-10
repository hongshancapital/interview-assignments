import assert from "assert"
import sinon from 'sinon'
import resp, { JsonSender } from '../../../src/libs/res'
import { InvalidCodeError } from "../../../src/errors"

describe('res util', () => {
    // 创建一个测试用的 sender
    const jsonSender: JsonSender = {
        json(o) {}
    }

    afterEach(() => {
        sinon.restore()
    })

    describe('jsonOK', () => {
        it('传入的 code 不合法，应抛异常', () => {
            assert.throws(() => {
                resp.jsonOK(jsonSender, {}, 100)
            }, InvalidCodeError)

            assert.throws(() => {
                resp.jsonOK(jsonSender, {}, 300)
            }, InvalidCodeError)
        })

        it('传入合法的 data 和 code，应正常执行，且正确调用了 JsonSender.json', () => {
            const spy = sinon.spy(jsonSender, 'json')

            assert.doesNotThrow(() => {
                resp.jsonOK(jsonSender)
                assert.equal(spy.calledWithExactly({ code: 200, message: 'OK', data: {} }), true)

                resp.jsonOK(jsonSender, { say:'hello' })
                assert.equal(spy.calledWithExactly({ code: 200, message: 'OK', data: { say: 'hello' } }), true)

                resp.jsonOK(jsonSender, { say: 'yes' }, 201)
                assert.equal(spy.calledWithExactly({ code: 201, message: 'OK', data: { say: 'yes' } }), true)

                resp.jsonOK(jsonSender, {}, 202, 'SUC')
                assert.equal(spy.calledWithExactly({ code: 202, message: 'SUC', data: {} }), true)
            })
        })
    })

    describe('jsonFail', () => {
        it('传入的 code 不合法，应抛异常', () => {
            assert.throws(() => {
                resp.jsonFail(jsonSender, 'error', 200)
            }, InvalidCodeError)

            assert.throws(() => {
                resp.jsonFail(jsonSender, 'error', 209)
            }, InvalidCodeError)
        })

        it('传入合法的 message 和 code，应正常执行，且正确调用了 JsonSender.json', () => {
            const spy = sinon.spy(jsonSender, 'json')

            assert.doesNotThrow(() => {
                resp.jsonFail(jsonSender, 'error')
                assert.equal(spy.calledWithExactly({ code: 500, message: 'error', data: {} }), true)

                resp.jsonFail(jsonSender, 'not found', 404)
                assert.equal(spy.calledWithExactly({ code: 404, message: 'not found', data: {} }), true)
            })

            sinon.assert.calledTwice(spy)
        })
    })
})