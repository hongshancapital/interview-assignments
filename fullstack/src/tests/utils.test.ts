import dotenv from 'dotenv'
dotenv.config()

import { isURL, encodeID, decodeID, splitURL } from "../utils";

test('need protocol', () => {
    expect(isURL("baidu.com")).toBeFalsy()
})

test('http', () => {
    expect(isURL("http://baidu.com")).toBeTruthy()
})

test('https', () => {
    expect(isURL("https://baidu.com")).toBeTruthy()
})

test('port', () => {
    expect(isURL("http://baidu.com:8080")).toBeTruthy()
})


test('encodeid', () => {
    expect(encodeID(12345)).toBe('yRNV6')
})

test('decodeid', () => {
    expect(decodeID("yRNV6")).toBe(12345)
})

test('split url https://baidu.com',() => {
    expect(splitURL('https://baidu.com')).toStrictEqual(["https://baidu.com","/"])
})

test('split url https://baidu.com/abc?d=3#124234',() => {
    expect(splitURL('https://baidu.com/abc?d=3#124234')).toStrictEqual(["https://baidu.com","/abc?d=3#124234"])
})