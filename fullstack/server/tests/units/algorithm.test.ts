import { number2Short } from '../../src/algorithm'

describe('algorithm', () => {
  it('should return a short url', async () => {
    let base62 = number2Short(1, 6)
    expect(base62.length).toEqual(6)
    base62 = number2Short(1, 8)
    expect(base62.length).toEqual(8)
  })
})