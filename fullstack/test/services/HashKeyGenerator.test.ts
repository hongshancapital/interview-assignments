import HashKeyGenerator from '../../src/services/HashKeyGenerator'
jest.mock('../../src/components/bloomFilter', () => {
  return {
    exists: jest
      .fn()
      .mockImplementationOnce(async () => true)
      .mockImplementationOnce(async () => false),
  }
})
describe('HashKeyGenarator ', () => {
  test('when hash collision generateKey sholud generate another key', async () => {
    const generator = new HashKeyGenerator({ shortUrlFilter: 'test' })
    await generator.generateKey(8)
  })
})
