import dotenv from 'dotenv'
dotenv.config()

import { isURL, encodeID, decodeID, splitURL, delay, formatURL } from '../utils';

describe('isURL test', () => {
    test('need protocol', () => {
        expect(isURL('baidu.com')).toBeFalsy()
    })

    test('http', () => {
        expect(isURL('http://baidu.com')).toBeTruthy()
    })

    test('https', () => {
        expect(isURL('https://baidu.com')).toBeTruthy()
    })

    test('port', () => {
        expect(isURL('http://baidu.com:8080')).toBeTruthy()
    })

})

describe('encode decode', () => {
    test('encodeid', () => {
        expect(encodeID(12345)).toBe('yRNV6')
    })

    test('decodeid', () => {
        expect(decodeID('yRNV6')).toBe(12345)
    })
})

describe('splitURL', () => {
    test('split url https://baidu.com', () => {
        expect(splitURL('https://baidu.com')).toStrictEqual(['https://baidu.com', '/'])
    })

    test('split url https://baidu.com/abc?d=3#124234', () => {
        expect(splitURL('https://baidu.com/abc?d=3#124234')).toStrictEqual(['https://baidu.com', '/abc?d=3#124234'])
    })
})

test('delay', async () => {
    await delay(500)

    expect(1).toBe(1)
})

test('format url', async () => {
    expect(formatURL('https://baidu.com')).toStrictEqual('https://baidu.com/')
    expect(formatURL('https://baidu.com?abd=ddd')).toStrictEqual('https://baidu.com/?abd=ddd')
})