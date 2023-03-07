import { describe, expect, test, jest } from '@jest/globals';
import {Request, Response} from 'express'
import checkParameter from './check'


describe('controller check', () => {
  const mockCallback = jest.fn()
  const mockErrorCallback = jest.fn()
  test('check pass has run callback', () => {
    const body = {
      longLink: 'https://www.baidu.com/abcde?aaa=bbb&ccc=dddeeecccsss',
    }
    checkParameter({ body } as Request, {send: mockErrorCallback} as any, mockCallback)
    expect(mockCallback.mock.calls).toHaveLength(1);
  })

  test('check error', () => {
    const body = {
      longLink: 'abcdefg',
    }
    

    checkParameter({ body } as Request, {
      send: mockErrorCallback
    } as any, mockCallback)


    expect(mockCallback.mock.calls).toHaveLength(0);
    expect(mockErrorCallback.mock.calls).toHaveLength(1)
  })
})