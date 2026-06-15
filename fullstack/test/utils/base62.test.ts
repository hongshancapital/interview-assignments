import * as base62 from '../../src/utils/base62'
describe('base62 encode', () => {
  test('encode Number.MAX_SAFE_INTEGER to equal FfGNdXsE7', () => {
    expect(base62.encode(Number.MAX_SAFE_INTEGER)).toBe('FfGNdXsE7')
  })
  test('encode -1 expect throw error', () => {
    expect(() => base62.encode(-1)).toThrowError(
      'num must be a positive integer.input: -1',
    )
  })
})
