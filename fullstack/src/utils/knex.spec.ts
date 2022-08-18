import { isErrorResponse, errorHandler } from './knex'

describe('Knex Util Test', () => {
  test('test is error response', () => {
    expect(isErrorResponse(null)).toEqual(true)
    expect(isErrorResponse({ code: 1 })).toEqual(true)
    expect(isErrorResponse([])).toEqual(false)
  })

  test('test error handler', () => {
    expect(errorHandler({ errno: 1, code: 'test error' }).code).toEqual(1)
  })
})
