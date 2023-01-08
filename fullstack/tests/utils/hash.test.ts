import { ToBase62Str, FromBase62Str } from '../../src/utils/hash'

describe('Test hash function', () => {
  const ids = [61, 62, 288, 6299, 10000, 30000, 10000000]
  const strs = ['z', '10', '4e', '1db', '2bI', '7ns', 'fxSK']

  it('ToBase62Str should work', () => {
    for(let i = 0; i <ids.length; i++) {
      expect(ToBase62Str(ids[i])).toBe(strs[i])
    }
  })

  it('FromBase62Str should work', () => {
    for(let i = 0; i <ids.length; i++) {
      expect(FromBase62Str(strs[i])).toBe(ids[i])
    }
  })
})
