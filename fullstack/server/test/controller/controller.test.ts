import { describe, expect, test, jest } from '@jest/globals';
import { Request, Response } from 'express'
import checkParameter from '../../middleware/Controller/checkParameter'

enum Methods {
  Post = 'POST',
  Get = 'GET'
}

describe('中间件', () => {
  const mockCallback = jest.fn()
  const mockErrorCallback = jest.fn()
  test('校验参数通过', () => {
    const body = {
      longLink: 'https://www.baidu.com/abcde?aaa=bbb&ccc=dddeeecccsss',
    }
    checkParameter({ body } as Request, {send: mockErrorCallback} as any, mockCallback)
    expect(mockCallback.mock.calls).toHaveLength(1);
  })

  describe('校验参数未通过', () => {
    test('post 请求', () => {
      const body = {
        longLink: '',
      }
      checkParameter({ body, method: Methods.Post } as Request, {} as any, mockCallback)
      expect(mockCallback.mock.calls).toHaveLength(1);
    })

    test('get 请求', () => {
      const query = {
        shortLink: '',
      }
      checkParameter({ query, method: Methods.Get } as unknown as Request, {} as any, mockCallback)
      expect(mockCallback.mock.calls).toHaveLength(1);
    })
    
  })
})