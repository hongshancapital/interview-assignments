import { describe, expect, test, jest } from '@jest/globals';
import {Request, Response} from 'express'
import checkParameter from './check'


describe('controller check', () => {
  test('sss', () => {
    const body = {
      longLink: 'https://www.baidu.com/abcde?aaa=bbb&ccc=dddeeecccsss',
    }
    const mockCallback = jest.fn()
    checkParameter({ body } as Request, {} as Response, mockCallback)
    expect(mockCallback.mock.calls).toHaveLength(1);
  })
})